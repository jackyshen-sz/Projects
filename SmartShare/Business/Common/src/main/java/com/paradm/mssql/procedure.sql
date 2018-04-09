-- create document start
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[CREATE_SMARTSHARE_DOCUMENT]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[CREATE_SMARTSHARE_DOCUMENT]
END
GO
CREATE PROCEDURE [dbo].[CREATE_SMARTSHARE_DOCUMENT]
	@docName AS NVARCHAR(255),
	@docType AS INT,
	@parentId AS INT,
  @rootId AS INT,
  @rootType AS INT,
	@inheritFlag AS INT,
	@permission AS NVARCHAR(2000),
	@effStartDate AS datetime,
  @effEndDate AS datetime,
	@refNo AS NVARCHAR(100),
  @description AS NVARCHAR(2000),
  @itemSize AS decimal(38,0),
  @encrypSize AS decimal(38,0),
	@itemStatus AS INT,
	@profileId AS INT,
	@index1 AS NVARCHAR(255),
	@index2 AS NVARCHAR(255),
	@index3 AS NVARCHAR(255),
	@index4 AS NVARCHAR(255),
	@index5 AS NVARCHAR(255),
	@index6 AS NVARCHAR(255),
	@index7 AS NVARCHAR(255),
	@index8 AS NVARCHAR(255),
	@index9 AS NVARCHAR(255),
	@index10 AS NVARCHAR(255),
	@userId AS INT,
	@recordStatus AS INT,
	@ext AS NVARCHAR(15),
	@segmentNo AS INT,
	@convertedId AS NVARCHAR(255),
	@convertedName AS NVARCHAR(255),
	@versionNo AS NVARCHAR(200),
	@sessionId AS NVARCHAR(255),
	@userIP AS NVARCHAR(255),
	@createDate AS datetime,
	@teamAlertIds AS NVARCHAR(2000),
	@alertMeIds AS NVARCHAR(2000),
	@createType AS INT,
	@sbName AS NVARCHAR(1000),
	@location AS NVARCHAR(1000),
	@returnExsited AS BIT,
	@locId AS INT,
	@md5 AS NVARCHAR(255)
AS

BEGIN
	SET NOCOUNT ON;
	DECLARE @docId INT;
	DECLARE @contentId INT;
	DECLARE @versionId INT;
	DECLARE @docPemId INT;
	DECLARE @docStructId INT;
	DECLARE @hierarchyId INT;
	DECLARE @docPemHierayId INT;
	DECLARE @docPemLevel INT;
	DECLARE @structId INT;
	DECLARE @docStrHierayId INT;
	DECLARE @docStrLevel INT;
	DECLARE @rulesPerId INT;
	DECLARE @auditTrailId INT;
	DECLARE @objectType NVARCHAR(30);
	DECLARE @accessType VARCHAR(3);
	DECLARE @alertValueId INT;
	DECLARE @alertType INT;
	DECLARE @teamAlertStatus INT;
	DECLARE @hasPermission AS char(1);
	DECLARE @error_msg AS NVARCHAR(255);
	DECLARE @doc_Id_exsited INT;

	BEGIN try
		IF NOT EXISTS (SELECT TOP 1 ID FROM SMARTSHARE_DOCUMENT WITH(ROWLOCK) WHERE DOC_NAME = @docName AND PARENT_ID = @parentId AND ROOT_ID = @rootId AND RECORD_STATUS = 0)
			BEGIN
				-- insert root
				IF @parentId = 0
					BEGIN
						INSERT INTO SMARTSHARE_ROOT WITH(ROWLOCK)(ROOT_NAME,ROOT_TYPE,LOC_ID,STORAGE_LIMIT,TOTAL_SIZE,OWNER_ID,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
						VALUES (@docName,@rootType,@locId,0,0,@userId,0,0,@userId,@createDate,@userId,@createDate);
						SELECT @rootId = SCOPE_IDENTITY();
					END

				-- insert document
				SET @docStructId = @parentId;
				SET @docPemId = @parentId;
				SET @teamAlertStatus = 0;
				IF @teamAlertIds IS NOT NULL
					BEGIN
						SET @teamAlertStatus = 1;
					END 
				INSERT INTO SMARTSHARE_DOCUMENT WITH(ROWLOCK)(DOC_NAME,DOC_TYPE,PARENT_ID,ROOT_ID,DOC_PEM_ID,DOC_STRUCT_ID,CUR_VERSION_ID,CREATE_TYPE,
				VERSION_COUNT,CUR_CONTENT_ID,CHECKOUT_USER_ID,INHERIT_FLAG,EFFECTIVE_START_DATE,EFFECTIVE_END_DATE,REFE_NO,DESCRIPTION,ITEM_SIZE,
				ITEM_STATUS,TEAM_ALERT_STATUS,PROFILE_ID,INDEX_1,INDEX_2,INDEX_3,INDEX_4,INDEX_5,INDEX_6,INDEX_7,INDEX_8,INDEX_9,INDEX_10,RECORD_STATUS,UPDATE_COUNT,
				CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
				VALUES (@docName,@docType,@parentId,@rootId,@docPemId,@docStructId,0,@createType,
				1,0,null,@inheritFlag,@effStartDate,@effEndDate,@refNo,@description,@itemSize,
				@itemStatus,@teamAlertStatus,@profileId,@index1,@index2,@index3,@index4,@index5,@index6,@index7,@index8,@index9,@index10,@recordStatus,0,
				@userId,@createDate,@userId,@createDate);
				SELECT @docId = SCOPE_IDENTITY();
				IF @docType = 0
					BEGIN
						SET @objectType = 'Z';
						SET @docStructId = @docId;
						SET @alertType = 1;
					END
				ELSE
					BEGIN
						SET @objectType = 'D';
						SET @docStructId = @parentId;
						SET @alertType = 0;
					END
				SET @docPemId = @docId;
				IF (@permission <> '' OR @permission IS NOT NULL)
					BEGIN
						SET @hasPermission = 'Y';
					END
				ELSE
					BEGIN
						SET @hasPermission = 'N';
						IF @docType = 1
							BEGIN
								SET @docPemId = @parentId;
							END
					END
					
				-- check if another thread has inserted the document with same parent id and same document name
				IF @returnExsited = 0
				BEGIN
					SET @doc_Id_exsited = (SELECT TOP 1 ID FROM SMARTSHARE_DOCUMENT WHERE DOC_NAME = @docName AND PARENT_ID = @parentId AND ROOT_ID = @rootId AND RECORD_STATUS = 0 AND ID <> @docId);
					IF @doc_Id_exsited IS NOT NULL 
					BEGIN
						DELETE FROM SMARTSHARE_DOCUMENT WHERE ID = @docId;
						SET @docId = @doc_Id_exsited;
						select * from SMARTSHARE_DOCUMENT WITH(NOLOCK) where id = @docId;
						return 0;
					END 
				END 
				
				-- insert document content
				INSERT INTO SMARTSHARE_DOC_CONTENT WITH(ROWLOCK)(CONVERTED_ID,CONVERTED_NAME,EXT,ITEM_SIZE,ENCRYPT_SIZE,SEGMENT_NO,MD5,RECORD_STATUS,UPDATE_COUNT,
				CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
				VALUES (@convertedId,@convertedName,@ext,@itemSize,@encrypSize,@segmentNo,@md5,0,0,@userId,@createDate,@userId,@createDate);
				SELECT @contentId = SCOPE_IDENTITY();
				
				-- insert document version
				INSERT INTO SMARTSHARE_DOC_VERSION WITH(ROWLOCK)(DOC_ID,VERSION_NO,VERSION_LABEL,PARENT_ID,CONTENT_ID,ITEM_STATUS,BK_ARCHIVE_ID,
				RESTORE_STATUS,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
				VALUES (@docId,@versionNo,'ROOT',0,@contentId,@itemStatus,null,null,0,0,@userId,@createDate,@userId,@createDate);
				SELECT @versionId = SCOPE_IDENTITY();
				
				-- update document cur_version_id and cur_conent_id
				UPDATE SMARTSHARE_DOCUMENT 
				SET CUR_VERSION_ID = @versionId,CUR_CONTENT_ID = @contentId,DOC_PEM_ID = @docPemId,DOC_STRUCT_ID = @docStructId
				WHERE ID = @docId;
				
				-- update root total size
				if @docType <> 0
					BEGIN
						UPDATE SMARTSHARE_ROOT SET TOTAL_SIZE = TOTAL_SIZE + @itemSize WHERE ID = @rootId;
					END
				
				-- insert hierarchy
				IF (@docType = 0 OR (@docType = 1 AND @hasPermission = 'Y'))
					BEGIN
						IF @hasPermission = 'Y'
							BEGIN
								DECLARE @rulesId INT;
								DECLARE @readPer INT;
								DECLARE @perStr NVARCHAR(255);
								DECLARE @str NVARCHAR(2000);
								DECLARE @permissionType INT;
								DECLARE @custPermission NVARCHAR(2000);
								WHILE ((@permission <> '' OR @permission IS NOT NULL) AND CHARINDEX(';',@permission) > 0)
									BEGIN
										SET @str = LEFT(@permission,CHARINDEX(';',@permission) - 1);
										SET @rulesId = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @permissionType = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @custPermission = LEFT(@str,CHARINDEX(',',@str) - 1);
										IF @custPermission = 'NULL'
											BEGIN
												SET @custPermission = '';
											END 
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @readPer = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @perStr = @str;
										--EXEC @rulesPerId = GET_NEXT_ID 'SMARTSHARE_DOC_RULES_PERMISSION';
										INSERT INTO SMARTSHARE_DOC_RULES_PERMISSION WITH(ROWLOCK)(RULES_ID,DOC_PEM_ID,PERMISSION_TYPE,READ_PERMISSION,PERMISSION,CUSTOM_PERMISSION)
										VALUES (@rulesId,@docPemId,@permissionType,@readPer,@perStr,@custPermission);
										SET @permission = STUFF(@permission,1,CHARINDEX(';',@permission),'');
									END
							END
						IF @parentId = 0
							BEGIN
								--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
								INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
								VALUES (@docId,@docId,0);
							END
						ELSE
							BEGIN
								IF @inheritFlag = 0
									BEGIN
										DECLARE hierarchy_cursor CURSOR  
										LOCAL FOR
										SELECT DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL FROM SMARTSHARE_DOC_HIERARCHY WHERE DOC_PEM_ID = @parentId ORDER BY DOC_PEM_LEVEL;
										OPEN hierarchy_cursor
										FETCH NEXT FROM hierarchy_cursor INTO @docPemHierayId,@docPemLevel
										WHILE (@@fetch_status = 0)
											BEGIN
												--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
												INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
												VALUES (@docId,@docPemHierayId,@docPemLevel);
												FETCH NEXT FROM hierarchy_cursor INTO @docPemHierayId,@docPemLevel
											END
										CLOSE hierarchy_cursor;
										DEALLOCATE hierarchy_cursor;
										--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
										INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
										VALUES (@docId,@docId,@docPemLevel + 1);
									END
								ELSE
									BEGIN
										--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
										INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
										VALUES (@docId,@docId,0);
									END
							END
					END
				-- insert struct
				IF @docType = 0
					BEGIN
						IF @parentId = 0
							BEGIN
								--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
								INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
								VALUES (@docId,@docId,0);
							END
						ELSE
							BEGIN
								DECLARE struct_cursor CURSOR  
								LOCAL FOR
								SELECT DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL FROM SMARTSHARE_DOC_STRUCT WHERE DOC_STRUCT_ID = @parentId ORDER BY DOC_STRUCT_LEVEL;
								OPEN struct_cursor
								FETCH NEXT FROM struct_cursor INTO @docStrHierayId,@docStrLevel
								WHILE (@@fetch_status = 0)
									BEGIN
										--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
										INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
										VALUES (@docId,@docStrHierayId,@docStrLevel);
										FETCH NEXT FROM struct_cursor INTO @docStrHierayId,@docStrLevel
									END
								CLOSE struct_cursor;
								DEALLOCATE struct_cursor;
								--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
								INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
								VALUES (@docId,@docId,@docStrLevel + 1);
							END
					END
				-- insert audit trail
				set @accessType = 'CF';
				IF @docType = 1
					BEGIN
						SET @accessType = 'C';
						IF (@createType = 3) 
							BEGIN
								IF (@recordStatus = 4) 
									BEGIN
										SET @accessType = 'P';
									END
								ELSE
									BEGIN
										SET @accessType = 'CFS';
									END
							END
						IF @createType = 2
							BEGIN
								SET @accessType = 'CFS';
							END
					END 
				INSERT INTO SMARTSHARE_DOC_AUDIT_TRAIL WITH(ROWLOCK) (ROOT_TYPE,OBJECT_TYPE,OBJECT_NAME,OBJECT_ID,VERSION_NUMBER,ACCESS_TYPE,
				SESSION_ID,IP_ADDRESS,ACCESSOR_ID,ACCESS_DATE,COMMENT1,COMMENT5)
				VALUES (@rootType,@objectType,@docName,@docId,@versionNo,@accessType,@sessionId,@userIP,@userId,@createDate,@sbName,@location);
				
				IF @rootType <> 0
					BEGIN
						IF @teamAlertIds IS NOT NULL
							BEGIN
								INSERT INTO SMARTSHARE_DOC_ALERT_VALUE WITH(ROWLOCK) (NOTIFY_TYPE,DOC_ID,ALERT_TYPE,ALERTOR,ACTION_DATE,
								STATUS,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
								VALUES (1,@docId,@alertType,@teamAlertIds,@createDate,
								@itemStatus,0,0,@userId,@createDate,@userId,@createDate);
							END
						IF @alertMeIds IS NOT NULL
							BEGIN
								INSERT INTO SMARTSHARE_DOC_ALERT_VALUE WITH(ROWLOCK) (NOTIFY_TYPE,DOC_ID,ALERT_TYPE,ALERTOR,ACTION_DATE,
								STATUS,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
								VALUES (0,@docId,@alertType,@alertMeIds,@createDate,
								@itemStatus,0,0,@userId,@createDate,@userId,@createDate);
							END
						
						-- insert document recent
						IF @docType <> 0
						  BEGIN
							  IF NOT EXISTS (SELECT ID FROM SMARTSHARE_DOC_RECENT WHERE DOC_ID = @docId AND ACCESSOR_ID = @userId)	
							 	  BEGIN
										DELETE FROM SMARTSHARE_DOC_RECENT 
										WHERE ACCESSOR_ID = @userId 
										AND ID NOT IN (SELECT TOP 100 ID FROM SMARTSHARE_DOC_RECENT WHERE ACCESSOR_ID = @userId ORDER BY ACCESS_DATE DESC);
										DECLARE @recentId INT;
										--EXEC @recentId = GET_NEXT_ID 'SMARTSHARE_DOC_RECENT';
										INSERT INTO SMARTSHARE_DOC_RECENT WITH(ROWLOCK)(DOC_ID,ACCESSOR_ID,ACCESS_DATE)
										VALUES (@docId,@userId,@createDate);
								  END
								ELSE
									BEGIN
										UPDATE SMARTSHARE_DOC_RECENT SET ACCESS_DATE = @createDate WHERE DOC_ID = @docId AND ACCESSOR_ID = @userId;
									END 
							END
					END
				-- insert doc virus info
				IF @docType <> 0
					BEGIN
						INSERT INTO SMARTSHARE_DOC_VIRUS WITH(ROWLOCK) (DOC_ID,DOC_CONTENT_ID,STATUS,PROCESS_MESSAGE,ERROR_NO,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
						VALUES (@docId,@contentId,0,'',0,0,0,@userId,@createDate,@userId,@createDate);
					END 
			END
	END try
	BEGIN catch
		select @error_msg=error_message()
		RAISERROR (@error_msg , 16, 1) WITH NOWAIT
		select error_number(),error_message(),error_state(),error_severity()
		return (0)
	END catch;
	select * from SMARTSHARE_DOCUMENT WITH(NOLOCK) where id = @docId;
END
GO
-- create document end

-- copy document start
IF EXISTS (SELECT * FROM dbo.sysobjects WHERE id = object_id(N'[dbo].[COPY_SMARTSHARE_DOCUMENT]') AND OBJECTPROPERTY(id, N'IsProcedure') = 1)
BEGIN
	DROP PROCEDURE [dbo].[COPY_SMARTSHARE_DOCUMENT]
END
GO
CREATE PROCEDURE [dbo].[COPY_SMARTSHARE_DOCUMENT]
	@docName AS NVARCHAR(255),
	@docType AS INT,
	@parentId AS INT,
  @rootId AS INT,
  @rootType AS INT,
	@inheritFlag AS INT,
	@permission AS NVARCHAR(2000),
	@effStartDate AS datetime,
  @effEndDate AS datetime,
	@refNo AS NVARCHAR(100),
  @description AS NVARCHAR(2000),
  @itemSize AS decimal(38,0),
	@itemStatus AS INT,
	@profileId AS INT,
	@index1 AS NVARCHAR(255),
	@index2 AS NVARCHAR(255),
	@index3 AS NVARCHAR(255),
	@index4 AS NVARCHAR(255),
	@index5 AS NVARCHAR(255),
	@index6 AS NVARCHAR(255),
	@index7 AS NVARCHAR(255),
	@index8 AS NVARCHAR(255),
	@index9 AS NVARCHAR(255),
	@index10 AS NVARCHAR(255),
	@userId AS INT,
	@recordStatus AS INT,
	@contentId AS INT,
	@versionNo AS NVARCHAR(200),
	@sessionId AS NVARCHAR(255),
	@userIP AS NVARCHAR(255),
	@createDate AS datetime,
	@createType AS INT,
	@locId AS INT
AS

BEGIN
	SET NOCOUNT ON;
	DECLARE @docId INT;
	DECLARE @versionId INT;
	DECLARE @docPemId INT;
	DECLARE @docStructId INT;
	DECLARE @hierarchyId INT;
	DECLARE @docPemHierayId INT;
	DECLARE @docPemLevel INT;
	DECLARE @structId INT;
	DECLARE @docStrHierayId INT;
	DECLARE @docStrLevel INT;
	DECLARE @rulesPerId INT;
	DECLARE @auditTrailId INT;
	DECLARE @objectType NVARCHAR(30);
	DECLARE @hasPermission AS char(1);
	DECLARE @error_msg AS NVARCHAR(255);

	BEGIN try
		--EXEC @docId = GET_NEXT_ID 'SMARTSHARE_DOCUMENT';
		--EXEC @versionId = GET_NEXT_ID 'SMARTSHARE_DOC_VERSION';

		IF NOT EXISTS (SELECT ID FROM SMARTSHARE_DOCUMENT WHERE DOC_NAME = @docName AND PARENT_ID = @parentId AND ROOT_ID = @rootId AND RECORD_STATUS = 0)
			BEGIN
				-- insert root
				IF @parentId = 0
					BEGIN
						--EXEC @rootId = GET_NEXT_ID 'SMARTSHARE_ROOT';
						INSERT INTO SMARTSHARE_ROOT WITH(ROWLOCK)(ROOT_NAME,ROOT_TYPE,LOC_ID,STORAGE_LIMIT,TOTAL_SIZE,OWNER_ID,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
						VALUES (@docName,@rootType,@locId,0,0,@userId,0,0,@userId,@createDate,@userId,@createDate);
						SELECT @rootId = SCOPE_IDENTITY();
					END
				-- insert document
				SET @docStructId = @parentId;
				SET @docPemId = @parentId;
				INSERT INTO SMARTSHARE_DOCUMENT WITH(ROWLOCK)(DOC_NAME,DOC_TYPE,PARENT_ID,ROOT_ID,DOC_PEM_ID,DOC_STRUCT_ID,CUR_VERSION_ID,CREATE_TYPE,
				VERSION_COUNT,CUR_CONTENT_ID,CHECKOUT_USER_ID,INHERIT_FLAG,EFFECTIVE_START_DATE,EFFECTIVE_END_DATE,REFE_NO,DESCRIPTION,ITEM_SIZE,
				ITEM_STATUS,TEAM_ALERT_STATUS,PROFILE_ID,INDEX_1,INDEX_2,INDEX_3,INDEX_4,INDEX_5,INDEX_6,INDEX_7,INDEX_8,INDEX_9,INDEX_10,RECORD_STATUS,UPDATE_COUNT,
				CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
				VALUES (@docName,@docType,@parentId,@rootId,@docPemId,@docStructId,0,@createType,
				1,@contentId,null,@inheritFlag,@effStartDate,@effEndDate,@refNo,@description,@itemSize,
				@itemStatus,0,@profileId,@index1,@index2,@index3,@index4,@index5,@index6,@index7,@index8,@index9,@index10,@recordStatus,0,
				@userId,@createDate,@userId,@createDate);
				SELECT @docId = SCOPE_IDENTITY();
				IF @docType = 0
					BEGIN
						SET @objectType = 'Z';
						SET @docStructId = @docId;
					END
				ELSE
					BEGIN
						SET @objectType = 'D';
						SET @docStructId = @parentId;
					END
				SET @docPemId = @docId;
				IF (@permission <> '' OR @permission IS NOT NULL)
					BEGIN
						SET @hasPermission = 'Y';
					END
				ELSE
					BEGIN
						SET @hasPermission = 'N';
						IF @docType = 1
							BEGIN
								SET @docPemId = @parentId;
							END
					END

				-- insert document version
				INSERT INTO SMARTSHARE_DOC_VERSION WITH(ROWLOCK)(DOC_ID,VERSION_NO,VERSION_LABEL,PARENT_ID,CONTENT_ID,ITEM_STATUS,BK_ARCHIVE_ID,
				RESTORE_STATUS,RECORD_STATUS,UPDATE_COUNT,CREATOR_ID,CREATE_DATE,UPDATER_ID,UPDATE_DATE)
				VALUES (@docId,@versionNo,'ROOT',0,@contentId,@itemStatus,null,null,0,0,@userId,@createDate,@userId,@createDate);
				SELECT @versionId = SCOPE_IDENTITY();
				
				-- update document cur_version_id
				UPDATE SMARTSHARE_DOCUMENT 
				SET CUR_VERSION_ID = @versionId,DOC_PEM_ID = @docPemId,DOC_STRUCT_ID = @docStructId
				WHERE ID = @docId;
				
				-- update root total size
				if @docType <> 0
					BEGIN
						UPDATE SMARTSHARE_ROOT SET TOTAL_SIZE = TOTAL_SIZE + @itemSize WHERE ID = @rootId;
					END
				
				-- insert hierarchy
				IF (@docType = 0 OR (@docType = 1 AND @hasPermission = 'Y'))
					BEGIN
						IF @hasPermission = 'Y'
							BEGIN
								DECLARE @rulesId INT;
								DECLARE @readPer INT;
								DECLARE @perStr NVARCHAR(255);
								DECLARE @str NVARCHAR(2000);
								DECLARE @permissionType INT;
								DECLARE @custPermission NVARCHAR(2000);
								WHILE ((@permission <> '' OR @permission IS NOT NULL) AND CHARINDEX(';',@permission) > 0)
									BEGIN
										SET @str = LEFT(@permission,CHARINDEX(';',@permission) - 1);
										SET @rulesId = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @permissionType = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @custPermission = LEFT(@str,CHARINDEX(',',@str) - 1);
										IF @custPermission = 'NULL'
											BEGIN
												SET @custPermission = '';
											END 
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @readPer = CAST(LEFT(@str,CHARINDEX(',',@str) - 1) AS INT);
										SET @str = STUFF(@str,1,CHARINDEX(',',@str),'');
										SET @perStr = @str;
										--EXEC @rulesPerId = GET_NEXT_ID 'SMARTSHARE_DOC_RULES_PERMISSION';
										INSERT INTO SMARTSHARE_DOC_RULES_PERMISSION WITH(ROWLOCK)(RULES_ID,DOC_PEM_ID,PERMISSION_TYPE,READ_PERMISSION,PERMISSION,CUSTOM_PERMISSION)
										VALUES (@rulesId,@docPemId,@permissionType,@readPer,@perStr,@custPermission);
										SET @permission = STUFF(@permission,1,CHARINDEX(';',@permission),'');
									END
							END
						IF @parentId = 0
							BEGIN
								--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
								INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
								VALUES (@docId,@docId,0);
							END
						ELSE
							BEGIN
								IF @inheritFlag = 0
									BEGIN
										DECLARE hierarchy_cursor CURSOR  
										LOCAL FOR
										SELECT DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL FROM SMARTSHARE_DOC_HIERARCHY WHERE DOC_PEM_ID = @parentId ORDER BY DOC_PEM_LEVEL;
										OPEN hierarchy_cursor
										FETCH NEXT FROM hierarchy_cursor INTO @docPemHierayId,@docPemLevel
										WHILE (@@fetch_status = 0)
											BEGIN
												--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
												INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
												VALUES (@docId,@docPemHierayId,@docPemLevel);
												FETCH NEXT FROM hierarchy_cursor INTO @docPemHierayId,@docPemLevel
											END
										CLOSE hierarchy_cursor;
										DEALLOCATE hierarchy_cursor;
										--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
										INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
										VALUES (@docId,@docId,@docPemLevel + 1);
									END
								ELSE
									BEGIN
										--EXEC @hierarchyId = GET_NEXT_ID 'SMARTSHARE_DOC_HIERARCHY';
										INSERT INTO SMARTSHARE_DOC_HIERARCHY WITH(ROWLOCK)(DOC_PEM_ID,DOC_PEM_HIERAY_ID,DOC_PEM_LEVEL)
										VALUES (@docId,@docId,0);
									END
							END
					END
				-- insert struct
				IF @docType = 0
					BEGIN
						IF @parentId = 0
							BEGIN
								--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
								INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
								VALUES (@docId,@docId,0);
							END
						ELSE
							BEGIN
								DECLARE struct_cursor CURSOR  
								LOCAL FOR
								SELECT DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL FROM SMARTSHARE_DOC_STRUCT WHERE DOC_STRUCT_ID = @parentId ORDER BY DOC_STRUCT_LEVEL;
								OPEN struct_cursor
								FETCH NEXT FROM struct_cursor INTO @docStrHierayId,@docStrLevel
								WHILE (@@fetch_status = 0)
									BEGIN
										--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
										INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
										VALUES (@docId,@docStrHierayId,@docStrLevel);
										FETCH NEXT FROM struct_cursor INTO @docStrHierayId,@docStrLevel
									END
								CLOSE struct_cursor;
								DEALLOCATE struct_cursor;
								--EXEC @structId = GET_NEXT_ID 'SMARTSHARE_DOC_STRUCT';
								INSERT INTO SMARTSHARE_DOC_STRUCT WITH(ROWLOCK)(DOC_STRUCT_ID,DOC_STRUCT_HIERAY_ID,DOC_STRUCT_LEVEL)
								VALUES (@docId,@docId,@docStrLevel + 1);
							END
					END
				
				IF @rootType <> 0
					BEGIN
						-- insert document recent
						IF @docType <> 0
						  BEGIN
							  IF NOT EXISTS (SELECT ID FROM SMARTSHARE_DOC_RECENT WHERE DOC_ID = @docId AND ACCESSOR_ID = @userId)	
							 	  BEGIN
										DELETE FROM SMARTSHARE_DOC_RECENT 
										WHERE ACCESSOR_ID = @userId 
										AND ID NOT IN (SELECT TOP 100 ID FROM SMARTSHARE_DOC_RECENT WHERE ACCESSOR_ID = @userId ORDER BY ACCESS_DATE DESC);
										DECLARE @recentId INT;
										--EXEC @recentId = GET_NEXT_ID 'SMARTSHARE_DOC_RECENT';
										INSERT INTO SMARTSHARE_DOC_RECENT WITH(ROWLOCK)(DOC_ID,ACCESSOR_ID,ACCESS_DATE)
										VALUES (@docId,@userId,@createDate);
								  END
								ELSE
									BEGIN
										UPDATE SMARTSHARE_DOC_RECENT SET ACCESS_DATE = @createDate WHERE DOC_ID = @docId AND ACCESSOR_ID = @userId;
									END 
							END
					END
			END
	END try
	BEGIN catch
		select @error_msg=error_message()
		RAISERROR (@error_msg , 16, 1) WITH NOWAIT
		select error_number(),error_message(),error_state(),error_severity()
		return (0)
	END catch;
	select * from SMARTSHARE_DOCUMENT WITH(NOLOCK) where id = @docId;
END
GO
-- copy document end