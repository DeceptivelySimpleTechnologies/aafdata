-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: ContentItemGroupContentItemAssociation.ContentItemGroupContentItemAssociation

INSERT INTO "ContentItemGroupContentItemAssociation"."ContentItemGroupContentItemAssociation"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "ContentItemGroupId", "ContentItemId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '2f2e1f97-49fa-4373-8183-f978f864947c', 0, 'contentitemgroupcontentitemassociation-none-unknown-unknown', -1, -1, '', 1, true, '00000000-0000-0000-0000-000000000000', '44f748a75115de676338c4d9212513dd82b02c124e58bb2762f8c6b81c799b14', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, 'f43e4fd6-2c16-408a-bdfe-7fce8ccbf5a0', 0, 'contentitemgroupcontentitemassociation-none-none-none', 0, 0, '', 1, true, '00000000-0000-0000-0000-000000000000', '0a3661ccfff99afc107832c7334c9bc373c689672ecd641f969539733ec5ed38', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
-- ("Id", "Uuid", "EntitySubtypeId", "TextKey", "ContentItemGroupId", "ContentItemId", "PublishedAtDateTimeUtc", "PublishedByInformationSystemUserId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
