/*
 * Copyright 2004-2020 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package org.h2.command.ddl;

import org.h2.api.ErrorCode;
import org.h2.command.CommandInterface;
import org.h2.constraint.ConstraintDomain;
import org.h2.engine.Database;
import org.h2.engine.Session;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;

/**
 * This class represents the statement ALTER DOMAIN ADD CONSTRAINT
 */
public class AlterDomainAddConstraint extends SchemaCommand {

    private String constraintName;
    private String domainName;
    private Expression checkExpression;
    private String comment;
    private boolean checkExisting;
    private boolean ifDomainExists;
    private final boolean ifNotExists;

    public AlterDomainAddConstraint(Session session, Schema schema, boolean ifNotExists) {
        super(session, schema);
        this.ifNotExists = ifNotExists;
    }

    public void setIfDomainExists(boolean b) {
        ifDomainExists = b;
    }

    private String generateConstraintName(Domain domain) {
        if (constraintName == null) {
            constraintName = getSchema().getUniqueDomainConstraintName(session, domain);
        }
        return constraintName;
    }

    @Override
    public int update() {
        try {
            return tryUpdate();
        } finally {
            getSchema().freeUniqueName(constraintName);
        }
    }

    /**
     * Try to execute the statement.
     *
     * @return the update count
     */
    private int tryUpdate() {
        session.commit(true);
        Domain domain = getSchema().findDomain(domainName);
        if (domain == null) {
            if (ifDomainExists) {
                return 0;
            }
            throw DbException.get(ErrorCode.DOMAIN_NOT_FOUND_1, domainName);
        }
        if (constraintName != null && getSchema().findConstraint(session, constraintName) != null) {
            if (ifNotExists) {
                return 0;
            }
            throw DbException.get(ErrorCode.CONSTRAINT_ALREADY_EXISTS_1, constraintName);
        }
        session.getUser().checkAdmin();
        Database db = session.getDatabase();
        db.lockMeta(session);

        int id = getObjectId();
        String name = generateConstraintName(domain);
        ConstraintDomain constraint = new ConstraintDomain(getSchema(), id, name, domain);
        constraint.setExpression(session, checkExpression);
        if (checkExisting) {
            constraint.checkExistingData(session);
        }
        constraint.setComment(comment);
        db.addSchemaObject(session, constraint);
        domain.addConstraint(constraint);
        return 0;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstraintName() {
        return constraintName;
    }

    @Override
    public int getType() {
        return CommandInterface.ALTER_DOMAIN_ADD_CONSTRAINT;
    }

    public void setCheckExpression(Expression expression) {
        this.checkExpression = expression;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCheckExisting(boolean b) {
        this.checkExisting = b;
    }

}
