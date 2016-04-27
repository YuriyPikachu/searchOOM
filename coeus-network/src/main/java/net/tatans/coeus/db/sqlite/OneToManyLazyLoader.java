package net.tatans.coeus.db.sqlite;


import java.util.ArrayList;
import java.util.List;

import net.tatans.coeus.network.tools.TatansDb;

/**
 *
 * һ�Զ��ӳټ�����
 * Created by pwy on 13-7-25.
 * @param <O> ����ʵ���class
 * @param <M> ���ʵ��class
 */
public class OneToManyLazyLoader<O,M> {
    O ownerEntity;
    Class<O> ownerClazz;
    Class<M> listItemClazz;
    TatansDb db;
    public OneToManyLazyLoader(O ownerEntity,Class<O> ownerClazz,Class<M> listItemclazz,TatansDb db){
        this.ownerEntity = ownerEntity;
        this.ownerClazz = ownerClazz;
        this.listItemClazz = listItemclazz;
        this.db = db;
    }
    List<M> entities;

    /**
     * �������δ���أ������loadOneToMany�������
     * @return
     */
    public List<M> getList(){
        if(entities==null){
            this.db.loadOneToMany((O)this.ownerEntity,this.ownerClazz,this.listItemClazz);
        }
        if(entities==null){
            entities =new ArrayList<M>();
        }
        return entities;
    }
    public void setList(List<M> value){
        entities = value;
    }

}
