package com.metechvn.common.hibernate;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.metamodel.RepresentationMode;
import org.hibernate.metamodel.spi.EntityRepresentationStrategy;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Iterator;

import static org.hibernate.internal.EmptyInterceptor.INSTANCE;

public interface DefaultInterceptor extends Interceptor, Serializable {

    @Override
    default boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return INSTANCE.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    default boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        return INSTANCE.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    default boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return INSTANCE.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    default void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        INSTANCE.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    default void onCollectionRecreate(Object collection, Object key) throws CallbackException {
        INSTANCE.onCollectionRecreate(collection, key);
    }

    @Override
    default void onCollectionRemove(Object collection, Object key) throws CallbackException {
        INSTANCE.onCollectionRemove(collection, key);
    }

    @Override
    default void onCollectionUpdate(Object collection, Object key) throws CallbackException {
        INSTANCE.onCollectionUpdate(collection, key);
    }

    @Override
    default void preFlush(Iterator<Object> entities) throws CallbackException {
        INSTANCE.preFlush(entities);
    }

    @Override
    default void postFlush(Iterator<Object> entities) throws CallbackException {
        INSTANCE.postFlush(entities);
    }

    @Override
    default Boolean isTransient(Object entity) {
        return INSTANCE.isTransient(entity);
    }

    @Override
    default int[] findDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return INSTANCE.findDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    default Object instantiate(String entityName, EntityRepresentationStrategy representationStrategy, Object id) throws CallbackException {
        return INSTANCE.instantiate(entityName, representationStrategy, id);
    }

    @Override
    default Object instantiate(String entityName, RepresentationMode representationMode, Object id) throws CallbackException {
        return INSTANCE.instantiate(entityName, representationMode, id);
    }

    @Override
    default String getEntityName(Object object) throws CallbackException {
        return INSTANCE.getEntityName(object);
    }

    @Override
    default Object getEntity(String entityName, Object id) throws CallbackException {
        return INSTANCE.getEntity(entityName, id);
    }

    @Override
    default void afterTransactionBegin(Transaction tx) {
        INSTANCE.afterTransactionBegin(tx);
    }

    @Override
    default void beforeTransactionCompletion(Transaction tx) {
        INSTANCE.beforeTransactionCompletion(tx);
    }

    @Override
    default void afterTransactionCompletion(Transaction tx) {
        INSTANCE.afterTransactionCompletion(tx);
    }
}
