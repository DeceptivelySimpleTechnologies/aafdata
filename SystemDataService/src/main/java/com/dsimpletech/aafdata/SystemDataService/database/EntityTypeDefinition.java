//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinition {

    @Getter private long Id;
    //    UUID Uuid;
    @Getter private long EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private String LocalizedName;
    @Getter private String LocalizedDescription;
    @Getter private String LocalizedAbbreviation;
    //    String VersionTag;
    //    long DataLocationEntitySubtypeId;
    //    long DataStructureEntitySubtypeId
    @Getter private Timestamp PublishedAtDateTimeUtc;
    @Getter private long PublishedByInformationSystemUserId;

    //    String ResourceName;
    @Getter private long Ordinal;
    @Getter boolean IsActive;
    //    UUID CorrelationUuid;
    //    String Digest;
    @Getter private Timestamp CreatedAtDateTimeUtc;
    @Getter private long CreatedByInformationSystemUserId;
    @Getter private Timestamp UpdatedAtDateTimeUtc;
    @Getter private long UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
    @Getter private long DeletedByInformationSystemUserId;

    public EntityTypeDefinition(long id, long entitySubtypeId, String textKey, String localizedName, String localizedDescription, String localizedAbbreviation, Timestamp publishedAtDateTimeUtc, long publishedByInformationSystemUserId, long ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, long createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, long updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, long deletedByInformationSystemUserId) {
        Id = id;
        EntitySubtypeId = entitySubtypeId;
        TextKey = textKey;
        LocalizedName = localizedName;
        LocalizedDescription = localizedDescription;
        LocalizedAbbreviation = localizedAbbreviation;
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
