package com.paradm.framework.taglib;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ControllerMessages implements Serializable {

  private static final long serialVersionUID = -5543676583469254715L;

  private static final Comparator<ControllerMessageItem> ACTION_ITEM_COMPARATOR = new Comparator<ControllerMessageItem>() {
    @Override
    public int compare(ControllerMessageItem o1, ControllerMessageItem o2) {
      return o1.getOrder() - o2.getOrder();
    }
  };

  public static final String GLOBAL_MESSAGE = "GLOBAL_MESSAGE";

  protected boolean accessed = false;

  protected HashMap<String, ControllerMessageItem> messages = new HashMap<String, ControllerMessageItem>();

  protected int iCount = 0;

  public ControllerMessages() {
    super();
  }

  public ControllerMessages(ControllerMessages messages) {
    super();
    this.add(messages);
  }

  public void add(String property, ControllerMessage message) {
    ControllerMessageItem item = messages.get(property);
    List<ControllerMessage> list;

    if (item == null) {
      list = new ArrayList<ControllerMessage>();
      item = new ControllerMessageItem(list, iCount++, property);

      messages.put(property, item);
    } else {
      list = item.getList();
    }

    list.add(message);
  }

  public void add(ControllerMessages messages) {
    if (messages == null) {
      return;
    }

    // loop over properties
    Iterator<String> props = messages.properties();

    while (props.hasNext()) {
      String property = props.next();

      // loop over messages for each property
      Iterator<ControllerMessage> msgs = messages.get(property);

      while (msgs.hasNext()) {
        ControllerMessage msg = msgs.next();

        this.add(property, msg);
      }
    }
  }

  public boolean isEmpty() {
    return (messages.isEmpty());
  }

  @SuppressWarnings("unchecked")
  public Iterator<ControllerMessage> get() {
    this.accessed = true;

    if (messages.isEmpty()) {
      return Collections.EMPTY_LIST.iterator();
    }

    List<ControllerMessage> results = new ArrayList<ControllerMessage>();
    List<ControllerMessageItem> actionItems = new ArrayList<ControllerMessageItem>();

    for (Iterator<ControllerMessageItem> i = messages.values().iterator(); i.hasNext();) {
      actionItems.add(i.next());
    }

    // Sort ActionMessageItems based on the initial order the
    // property/key was added to ActionMessages.
    Collections.sort(actionItems, ACTION_ITEM_COMPARATOR);

    for (Iterator<ControllerMessageItem> i = actionItems.iterator(); i.hasNext();) {
      ControllerMessageItem ami = i.next();

      for (Iterator<ControllerMessage> msgsIter = ami.getList().iterator(); msgsIter.hasNext();) {
        results.add(msgsIter.next());
      }
    }
    return results.iterator();
  }

  @SuppressWarnings("unchecked")
  public Iterator<ControllerMessage> get(String property) {
    this.accessed = true;

    ControllerMessageItem item = (ControllerMessageItem) messages.get(property);

    if (item == null) {
      return (Collections.EMPTY_LIST.iterator());
    } else {
      return (item.getList().iterator());
    }
  }

  @SuppressWarnings("unchecked")
  public Iterator<String> properties() {
    if (messages.isEmpty()) {
      return Collections.EMPTY_LIST.iterator();
    }

    List<String> results = new ArrayList<String>();
    List<ControllerMessageItem> actionItems = new ArrayList<ControllerMessageItem>();

    for (Iterator<ControllerMessageItem> i = messages.values().iterator(); i.hasNext();) {
      actionItems.add(i.next());
    }

    // Sort ActionMessageItems based on the initial order the
    // property/key was added to ActionMessages.
    Collections.sort(actionItems, ACTION_ITEM_COMPARATOR);
    for (Iterator<ControllerMessageItem> i = actionItems.iterator(); i.hasNext();) {
      ControllerMessageItem ami = i.next();
      results.add(ami.getProperty());
    }

    return results.iterator();
  }

  protected class ControllerMessageItem implements Serializable {

    private static final long serialVersionUID = -2850672622882099941L;

    protected List<ControllerMessage> list = null;

    protected int iOrder = 0;

    protected String property = null;

    public ControllerMessageItem(List<ControllerMessage> list, int iOrder, String property) {
      this.list = list;
      this.iOrder = iOrder;
      this.property = property;
    }

    public List<ControllerMessage> getList() {
      return list;
    }

    public void setList(List<ControllerMessage> list) {
      this.list = list;
    }

    public int getOrder() {
      return iOrder;
    }

    public void setOrder(int iOrder) {
      this.iOrder = iOrder;
    }

    public String getProperty() {
      return property;
    }

    public void setProperty(String property) {
      this.property = property;
    }

    public String toString() {
      return this.list.toString();
    }
  }

}
