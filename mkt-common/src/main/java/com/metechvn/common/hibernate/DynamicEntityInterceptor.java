package com.metechvn.common.hibernate;

import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.events.DynamicEntityDeletedEvent;
import com.metechvn.dynamic.events.DynamicEntitySavedEvent;
import com.metechvn.dynamic.events.DynamicEntityTypeSavedEvent;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DynamicEntityInterceptor implements Interceptor {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConcurrentMap<Object, Object> savedBatches = new ConcurrentHashMap<>();
    private final ConcurrentMap<Object, Object> deletedBatches = new ConcurrentHashMap<>();

    public DynamicEntityInterceptor(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        var res = Interceptor.super.onSave(entity, id, state, propertyNames, types);

        if (entity instanceof DynamicEntityType det) {
            applicationEventPublisher.publishEvent(new DynamicEntityTypeSavedEvent(det));
        } else if (entity instanceof DynamicEntity de) {
            savedBatches.put(id, de);
        }

        return res;
    }

    @Override
    public boolean onFlushDirty(
            Object entity,
            Object id,
            Object[] currentState,
            Object[] previousState,
            String[] propertyNames,
            Type[] types) throws CallbackException {

        var res = Interceptor.super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);

        if (entity instanceof DynamicEntityType det) {
            applicationEventPublisher.publishEvent(new DynamicEntityTypeSavedEvent(det));
        } else if (entity instanceof DynamicEntity de) {
            savedBatches.put(id, de);
        }

        return res;
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        Interceptor.super.onDelete(entity, id, state, propertyNames, types);

        if (entity instanceof DynamicEntityType det) {
            applicationEventPublisher.publishEvent(new DynamicEntityTypeSavedEvent(det));
        } else if (entity instanceof DynamicEntity de) {
            deletedBatches.put(id, de);
        }
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        Interceptor.super.afterTransactionCompletion(tx);

        DynamicEntitySavedEvent savedEvent = null;
        DynamicEntityDeletedEvent deletedEvent = null;

        if (!savedBatches.isEmpty()) {
            savedEvent = new DynamicEntitySavedEvent(savedBatches.values().stream().toList());
            savedBatches.clear();
        }

        if (!deletedBatches.isEmpty()) {
            deletedEvent = new DynamicEntityDeletedEvent(deletedBatches.values().stream().toList());
            deletedBatches.clear();
        }

        if (savedEvent != null) {
            applicationEventPublisher.publishEvent(savedEvent);
        }
        if (deletedEvent != null) {
            applicationEventPublisher.publishEvent(deletedEvent);
        }
    }

}
