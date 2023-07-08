package com.paradm.sse.services.user.impl;

import com.paradm.sse.services.user.IUserRecordService;
import com.paradm.sse.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Jacky.shen
 * @create data 2020/5/12
 */
@Service
public class UserService implements IUserService, UserDetailsService {

  @Autowired
  private IUserRecordService userRecordService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return null;
  }
}
