-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.

-- Table: Employee.Employee

INSERT INTO "Employee"."Employee"
    ("Id", "Uuid", "EntitySubtypeId", "TextKey", "OrganizationId", "PersonId", "ResourceName", "Ordinal", "IsActive", "CorrelationUuid", "Digest", "CreatedAtDateTimeUtc", "CreatedByInformationSystemUserId", "UpdatedAtDateTimeUtc", "UpdatedByInformationSystemUserId", "DeletedAtDateTimeUtc", "DeletedByInformationSystemUserId")
VALUES
    (-1, '4e8e579c-8634-491a-88cf-8ca1787134ca', 0, 'employee-unknown-unknown', -1, -1, '', -1, true, '00000000-0000-0000-0000-000000000000', '7934557e76cb304b54e4cda6b77058818b7600e0329a7f7a43f4c05640180627', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (0, 'f0a576df-e340-47c9-a969-4d0a587a29ae', 0, 'employee-none-none', 0, 0, '', -1, true, '00000000-0000-0000-0000-000000000000', '3c9372e9ab61ca08d567ce606411c194e13df092c6d8ce302c3e513401f947f0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0),
    (1, 'babc62c6-ba46-4921-862b-8b9ed07e4e11', 0, 'employee-none-amysaccounting-amyanderson', 3, 1, '', -1, true, '00000000-0000-0000-0000-000000000000', '8eba4c632634dc52e1ab6984282478c201bcb8096c1345535d14c650d145f4d0', '2022-06-01 00:00:00.000', 0, '2022-06-01 00:00:00.000', 0, '9999-12-31 23:59:59.999', 0)
;