//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.EntityDataMicroservice.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinitionEntityTypeAttributeAssociation {

    @Getter private int Id;
//    UUID Uuid;
    @Getter private int EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private int EntityTypeDefinitionId;
    @Getter private int EntityTypeAttributeId;

//    String ResourceName;
    @Getter private int Ordinal;
    @Getter private boolean IsActive;
//    UUID CorrelationUuid;
//    String Digest;
//    Timestamp CreatedAtDateTimeUtc;
//    int CreatedByInformationSystemUserId;
//    Timestamp UpdatedAtDateTimeUtc;
//    int UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
//    int DeletedByInformationSystemUserId;

    public EntityTypeDefinitionEntityTypeAttributeAssociation(int id, int entitySubtypeId, String textKey, int entityTypeDefinitionId, int entityTypeAttributeId, int ordinal, boolean isActive, Timestamp deletedAtDateTimeUtc) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        EntityTypeDefinitionId = entityTypeDefinitionId;
        EntityTypeAttributeId = entityTypeAttributeId;
        Ordinal = ordinal;
        IsActive = isActive;
        DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
    }
}
