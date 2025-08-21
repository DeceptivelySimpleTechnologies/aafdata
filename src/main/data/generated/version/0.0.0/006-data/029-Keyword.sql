-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: Keyword.Keyword

INSERT INTO "Keyword"."Keyword"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '406282e2-06e7-49d6-ba90-c0ceecfc0fab', 0, 'keyword-unknown', 'Unknown', 'Represents an as yet undetermined Keyword value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Kywd Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', 'e21ab36252e27c476a1f4b93e53d66b876b5cba09e821b4a8ee91e0356263979', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '13681031-c70f-4f3e-a93c-a1cd88bd489d', 0, 'keyword-none', 'None', 'Represents a value indicating that Keyword does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Kywd None', '', -1, true, '00000000-0000-0000-0000-000000000000', '9d85d3825d004952bec34fbd55ae2a153436986c96aec5a1da943568a5654a16', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
