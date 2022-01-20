package com.example.todo.Model;

public class Menu {
    int iconName;
    String menuName;
    int idMenu;


    public int getIconName() {
        return iconName;
    }

    public Menu() {
    }

    public Menu(int iconName, String menuName) {
        this.iconName = iconName;
        this.menuName = menuName;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public void setIconName(int iconName) {
        this.iconName = iconName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
