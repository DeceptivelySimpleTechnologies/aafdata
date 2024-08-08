-- NOTE: Run this script as the custom AafCorePublisher database role/account, which should be created by the AafCoreOwner role.
-- Table: Employee.Employee

-- DROP TABLE "Employee"."Employee";

CREATE TABLE "Employee"."Employee"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(100) COLLATE pg_catalog."default" NOT NULL,

    "OrganizationId" bigint NOT NULL,
    "PersonId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "Ordinal" bigint NOT NULL,
    "IsActive" boolean NOT NULL,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "Employee_PK" PRIMARY KEY ("Id")

    CONSTRAINT "Employee_CHK_TextKey" CHECK ("TextKey" ~* "^[a-z0-9-]+$")

    CONSTRAINT "Employee_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc")
    CONSTRAINT "Employee_UQ1_EntitySubtypeId_OrganizationId_PersonId_DeletedAtDateTimeUtc" UNIQUE ("EntitySubtypeId", "OrganizationId", "PersonId", "DeletedAtDateTimeUtc")

    CONSTRAINT "Employee_FK_EntitySubtypeId" FOREIGN KEY ("Id") REFERENCES "EntitySubtype"("Id")
    CONSTRAINT "Employee_FK_OrganizationId" FOREIGN KEY ("Id") REFERENCES "Organization"("Id")
    CONSTRAINT "Employee_FK_PersonId" FOREIGN KEY ("Id") REFERENCES "Person"("Id")
)

    TABLESPACE pg_default;

ALTER TABLE "Employee"."Employee"
    OWNER to "AafCorePublisher";