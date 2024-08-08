-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.

-- Table: Organization.Organization

INSERT INTO "Organization"."Organization"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "LegalName", "LocalizedDescription", "LocalizedAbbreviation", "DoingBusinessAs", "LegalFormationAtDateTimeUtc", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '571c85e6-9916-4dc5-a528-0e5ec8583fca', 0, 'organization-unknown', 'Unknown', 'Represents an as yet undetermined Organization value, often used as an initial default value or as a temporary placeholder that will be updated in the future', 'Org Ukwn', '', -1, true, '00000000-0000-0000-0000-000000000000', '967cf9d0a6831f8fa88a906da0ff7dd94c02203cc777cb11190c1a9d329295cb', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, '7e640d3f-42fa-42ea-bc8c-33f13935f06b', 0, 'organization-none', 'None', 'Represents a value indicating that Organization does not apply in this case, is intentionally omitted or undefined, or is not relevant', 'Org None', '', -1, true, '00000000-0000-0000-0000-000000000000', '70803e7b64abb4fca64e03495b8d4f833e61aa9f8b9d4b6a7f26519c8ce3cff0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'aeb67d9d-e8b9-4085-94e7-421bd3adb630', 51, 'organization-ccorporation-deceptivelysimpletechnologiesinc', 'Deceptively Simple Technologies, Inc', 'Revolutionizing the Way the World Builds Software', 'DST', '', '2009-11-09 19:00:00.000', '', -1, true, '00000000-0000-0000-0000-000000000000', '44ed61469acbf2cd63f1e53b6f7b9654baa4b12f188226f0ce9acf6516d7d018', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (2, '2954caa4-3adf-4c0c-8cbf-fc892f18ab14', 52, 'organization-scorporation-cygnussolutionsinc', 'Cygnus Solutions, Inc', 'Technology in the Service of Business', 'Cygnus', 'Cygnus Technology Services', '2003-02-06 19:00:00.000', '', -1, true, '00000000-0000-0000-0000-000000000000', 'd601d0c53d1e811acfc611534778272303fbd038cb0cec9284f55f8be418d5e7', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (3, '316da14e-eed7-415e-b79a-006126ce0e74', 55, 'organization-soleproprietorship-amysaccounting', 'Amy''s Accounting', 'Small business accounting services', 'Amy''s Acctg', '', '2022-06-01 00:00:00.000', '', -1, true, '00000000-0000-0000-0000-000000000000', '29c51641b8cb65f359c125639e735f41eb39b7a479d8207e19d92f8ec379d7d5', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;
