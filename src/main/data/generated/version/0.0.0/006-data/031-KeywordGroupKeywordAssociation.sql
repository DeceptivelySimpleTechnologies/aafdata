-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: KeywordGroupKeywordAssociation.KeywordGroupKeywordAssociation

INSERT INTO "KeywordGroupKeywordAssociation"."KeywordGroupKeywordAssociation"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "KeywordGroupId", "KeywordId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '24c529d3-8a1c-48f8-be84-9790bd135e2b', 0, 'keywordgroupkeywordassociation-unknown-unknown', -1, -1, '', 1, true, '00000000-0000-0000-0000-000000000000', 'fd1df16867e0eb0cb9c57fb07eedc059589ab710b589dca88e183c787df1ab09', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, 'fafe8b96-95a6-47dc-b8e9-705979dbe48e', 0, 'keywordgroupkeywordassociation-none-none', 0, 0, '', 1, true, '00000000-0000-0000-0000-000000000000', '72405240ec635d48233872a85015de5846ee8cd17d7a4a1ae01e66d70f26c2a0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
-- ("Id", "Uuid", "EntitySubtypeId", "TextKey", "KeywordGroupId", "KeywordId", "PublishedAtDateTimeUtc", "PublishedByInformationSystemUserId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
