-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.

-- Table: TelephoneNumber.TelephoneNumber

INSERT INTO "TelephoneNumber"."TelephoneNumber"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LocalizedName", "LocalizedDescription", "LocalizedAbbreviation", "CountryCode", "SubscriberNumber", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (1, 'a23e4191-0e92-442a-bb7a-f6daab3024a0', 79, 'telephonenumber-local-uscongress', 'The United States Congress', ' The U.S. House of Representatives switchboard operator', 'USA', '1', '2022243121', '', -1, true, '00000000-0000-0000-0000-000000000000', '03b5e03bfefe9c9f2396dc3cb737265607c06240c1d839fb6c12343de021c256', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
