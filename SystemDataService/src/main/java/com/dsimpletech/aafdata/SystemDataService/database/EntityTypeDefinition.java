//NOTE: Copyright © 2003-2024 Deceptively Simple Technologies Inc. Some rights reserved. Please see the aafdata/LICENSE.txt file for details.

package com.dsimpletech.aafdata.SystemDataService.database;

import lombok.Getter;

import java.sql.Timestamp;


public class EntityTypeDefinition {

    @Getter private int Id;
    //    UUID Uuid;
    @Getter private int EntitySubtypeId;
    @Getter private String TextKey;

    @Getter private String LocalizedName;
    @Getter private String LocalizedDescription;
    @Getter private String LocalizedAbbreviation;
    //    String VersionTag;
    //    int DataLocationEntitySubtypeId;
    //    int DataStructureEntitySubtypeId
    @Getter private Timestamp PublishedAtDateTimeUtc;
    @Getter private int PublishedByInformationSystemUserId;

    //    String ResourceName;
    @Getter private int Ordinal;
    @Getter private boolean IsActive;
    //    UUID CorrelationUuid;
    //    String Digest;
    @Getter private Timestamp CreatedAtDateTimeUtc;
    @Getter private int CreatedByInformationSystemUserId;
    @Getter private Timestamp UpdatedAtDateTimeUtc;
    @Getter private int UpdatedByInformationSystemUserId;
    @Getter private Timestamp DeletedAtDateTimeUtc;
    @Getter private int DeletedByInformationSystemUserId;

    public EntityTypeDefinition(int id, int entitySubtypeId, String textKey, String localizedName, String localizedDescription, String localizedAbbreviation, Timestamp publishedAtDateTimeUtc, int publishedByInformationSystemUserId, int ordinal, boolean isActive, Timestamp createdAtDateTimeUtc, int createdByInformationSystemUserId, Timestamp updatedAtDateTimeUtc, int updatedByInformationSystemUserId, Timestamp deletedAtDateTimeUtc, int deletedByInformationSystemUserId) {
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
