package com.retailstreet.mobilepos.View.ExpandableNavigation;

/**
 * Created by anupamchugh on 22/12/17.
 */

public class MenuModel {

    public String menuName;
     public int   icon;
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, int icon) {

        this.menuName = menuName;
        this.icon = icon;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
