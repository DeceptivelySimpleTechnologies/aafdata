//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinitionEntityTypeAttributeAssociation {

    @Getter private long Id;
    //    UUID Uuid;
    @Getter private long EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private long EntityTypeDefinitionId;
    @Getter private long EntityTypeAttributeId;
    @Getter Timestamp PublishedAtDateTimeUtc;
    @Getter long PublishedByInformationSystemUserId;

    //    String ResourceName;
    @Getter private long Ordinal;
    @Getter private boolean IsActive;
    //    UUID CorrelationUuid;
    //    String Digest;
    @Getter Timestamp CreatedAtDateTimeUtc;
    @Getter long CreatedByInformationSystemUserId;
    @Getter Timestamp UpdatedAtDateTimeUtc;
    @Getter long UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
    @Getter long DeletedByInformationSystemUserId;

    public EntityTypeDefinitionEntityTypeAttributeAssociation(long id, long entitySubtypeId, String textKey, long entityTypeDefinitionId, long entityTypeAttributeId, Timestamp publishedAtDateTimeUtc, long publishedByInformationSystemUserId, long ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, long createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, long updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, long deletedByInformationSystemUserId) {
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
