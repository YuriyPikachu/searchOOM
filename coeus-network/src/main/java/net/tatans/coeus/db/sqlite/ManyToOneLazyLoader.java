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
public class ManyToOneLazyLoader<M,O> {
    M manyEntity;
    Class<M> manyClazz;
    Class<O> oneClazz;
    TatansDb db;
    /**
     * ����
     */
    private Object fieldValue;
    public ManyToOneLazyLoader(M manyEntity, Class<M> manyClazz, Class<O> oneClazz, TatansDb db){
        this.manyEntity = manyEntity;
        this.manyClazz = manyClazz;
        this.oneClazz = oneClazz;
        this.db = db;
    }
    O oneEntity;
    boolean hasLoaded = false;

    /**
     * �������δ���أ������loadManyToOne�������
     * @return
     */
    public O get(){
        if(oneEntity==null && !hasLoaded){
            this.db.loadManyToOne(null,this.manyEntity,this.manyClazz,this.oneClazz);
            hasLoaded = true;
        }
        return oneEntity;
    }
    public void set(O value){
        oneEntity = value;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}
