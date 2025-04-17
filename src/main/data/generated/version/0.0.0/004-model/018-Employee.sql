-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: Employee.Employee

-- DROP TABLE "Employee"."Employee";

CREATE TABLE "Employee"."Employee"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "OrganizationId" bigint NOT NULL,
    "PersonId" bigint NOT NULL,

    "ResourceName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Ordinal" bigint NOT NULL DEFAULT -1,
    "IsActive" boolean NOT NULL DEFAULT true,
    "CorrelationUuid" uuid NOT NULL,
    "Digest" character varying(500) COLLATE pg_catalog."default" NOT NULL,
    "CreatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "CreatedByInformationSystemUserId" bigint NOT NULL,
    "UpdatedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "UpdatedByInformationSystemUserId" bigint NOT NULL,
    "DeletedAtDateTimeUtc" timestamp without time zone NOT NULL,
    "DeletedByInformationSystemUserId" bigint NOT NULL,

    CONSTRAINT "Employee_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "Employee_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "Employee_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "Employee_UQ1_EntSubId_OrgId_PersonId_DeletedAtDateTimeUtc" UNIQUE ("EntitySubtypeId", "OrganizationId", "PersonId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "Employee_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "Employee_FK_OrganizationId" FOREIGN KEY ("OrganizationId") REFERENCES "Organization"."Organization"("Id"),
    CONSTRAINT "Employee_FK_PersonId" FOREIGN KEY ("PersonId") REFERENCES "Person"."Person"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "Employee_IDX_OrganizationId" ON "Employee"."Employee" ("OrganizationId");
CREATE INDEX "Employee_IDX_PersonId" ON "Employee"."Employee" ("PersonId")
