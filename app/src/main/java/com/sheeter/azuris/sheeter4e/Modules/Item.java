package com.sheeter.azuris.sheeter4e.Modules;

/**
 * Created by Eric on 6/5/2017.
 */

public class Item {
    private String name;
    private String nickName;
    private int quantity;
    private Boolean equipped;
    private ItemType type;
    private Boolean isMagic;

    public Item(){
        this.isMagic = false;
    }

    public Item(String name, Boolean equipped, ItemType type) {
        this.name = name;
        this.equipped = equipped;
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return nickName == null ? name : nickName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(Boolean equipped) {
        this.equipped = equipped;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setType(String type){
        switch (type){
            case "Armor":
                this.type = ItemType.ARMOR;
                break;
            case "Weapon":
                this.type = ItemType.WEAPON;
                break;
            case "Gear":
                this.type = ItemType.GEAR;
                break;
            default:
                this.type = ItemType.GEAR;
        }
    }

    public Boolean isMagic() {
        return isMagic;
    }

    public void setMagic(Boolean magic) {
        isMagic = magic;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
