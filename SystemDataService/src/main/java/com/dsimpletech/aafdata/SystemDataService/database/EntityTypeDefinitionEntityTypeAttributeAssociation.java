//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinitionEntityTypeAttributeAssociation {

    @Getter private int Id;
    //    UUID Uuid;
    @Getter private int EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private int EntityTypeDefinitionId;
    @Getter private int EntityTypeAttributeId;
    @Getter Timestamp PublishedAtDateTimeUtc;
    @Getter int PublishedByInformationSystemUserId;

    //    String ResourceName;
    @Getter private int Ordinal;
    @Getter private boolean IsActive;
    //    UUID CorrelationUuid;
    //    String Digest;
    @Getter Timestamp CreatedAtDateTimeUtc;
    @Getter int CreatedByInformationSystemUserId;
    @Getter Timestamp UpdatedAtDateTimeUtc;
    @Getter int UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
    @Getter int DeletedByInformationSystemUserId;

    public EntityTypeDefinitionEntityTypeAttributeAssociation(int id, int entitySubtypeId, String textKey, int entityTypeDefinitionId, int entityTypeAttributeId, Timestamp publishedAtDateTimeUtc, int publishedByInformationSystemUserId, int ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, int createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, int updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, int deletedByInformationSystemUserId) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        EntityTypeDefinitionId = entityTypeDefinitionId;
        EntityTypeAttributeId = entityTypeAttributeId;
        PublishedAtDateTimeUtc = publishedAtDateTimeUtc;
        PublishedByInformationSystemUserId = publishedByInformationSystemUserId;
        Ordinal = ordinal;
        IsActive = isActive;
        CreatedAtDateTimeUtc = createdAtDateTimeUtc;
        CreatedByInformationSystemUserId = createdByInformationSystemUserId;
        UpdatedAtDateTimeUtc = updatedAtDateTimeUtc;
        UpdatedByInformationSystemUserId = updatedByInformationSystemUserId;
        DeletedAtDateTimeUtc = deletedAtDateTimeUtc;
        DeletedByInformationSystemUserId = deletedByInformationSystemUserId;
    }
}
