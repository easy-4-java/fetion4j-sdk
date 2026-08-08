/*
 * Copyright (C) 2012, Apexes Network Technology. All rights reserved.
 * 
 *       http://www.apexes.net
 * 
 */
package net.apexes.fetion4j.core.user;

/**
 * Represents a buddy (friend) in the Fetion contact list, extending
 * {@link Personal} with a {@link Relation} status.
 *
 * @author <a href="https://github.com/loong10k">@Loong Wan</a>
 * @since 3.0.0
 * @see net.apexes.fetion4j.core.user.Personal
 * @see net.apexes.fetion4j.core.user.Relation
 */
public class Buddy extends Personal {
    
    /**
     * 关系
     */
    private Relation relation;
    
    public Buddy() {
    }
    
    public Buddy(int userId, String uri, String name, Relation relation) {
        super(userId, uri, name);
        this.relation = relation;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
    
    @Override
    public String toString() {
        return "Buddy{" 
                + "version=" + getVersion()
                + ", uri=" + getUri() 
                + ", userId=" + getUserId() 
                + ", name=" + getName()
                + ", sid=" + getSid()
                + ", mobileNo=" + getMobileNo()
                + ", nickname=" + getNickname()
                + ", impresa=" + getImpresa()
                + ", carrier=" + getCarrier()
                + ", carrierStatus=" + getCarrierStatus()
                + ", smsOnlineStatus=" + getSmsOnlineStatus()
                + ", presence=" + getPresence()
                + ", relation=" + relation
                + '}';
    }
    
}
