package com.retailstreet.mobilepos.View.ExpandableNavigation;

/**
 * Created by anupamchugh on 22/12/17.
 */

/**
 * Modified by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
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
