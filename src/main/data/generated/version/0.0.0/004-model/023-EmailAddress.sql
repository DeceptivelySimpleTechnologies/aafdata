-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: EmailAddress.EmailAddress

-- DROP TABLE "EmailAddress"."EmailAddress";

CREATE TABLE "EmailAddress"."EmailAddress"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,
    "DisplayName" character varying(100) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "LocalPart" character varying(64) COLLATE pg_catalog."default" NOT NULL,
    "DomainName" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "InternetDomainLabelHierarchyId" bigint NOT NULL,

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

    CONSTRAINT "EmailAddress_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "EmailAddress_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "EmailAddress_UQ1_LocalPart_DomainName_IntDomLblHirId_DeletedAtDateTimeUtc" UNIQUE ("LocalPart", "DomainName", "InternetDomainLabelHierarchyId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "EmailAddress_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype" ("Id"),
    CONSTRAINT "EmailAddress_FK_IntDomLblHirId" FOREIGN KEY ("InternetDomainLabelHierarchyId") REFERENCES "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy" ("Id"),

    CONSTRAINT "EmailAddress_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$')
)

    TABLESPACE pg_default;

CREATE INDEX "EmailAddress_IDX_LocalizedName" ON "EmailAddress"."EmailAddress" ("LocalizedName");
CREATE INDEX "EmailAddress_IDX_DisplayName" ON "EmailAddress"."EmailAddress" ("DisplayName");
CREATE INDEX "EmailAddress_IDX_LocalPart" ON "EmailAddress"."EmailAddress" ("LocalPart");
CREATE INDEX "EmailAddress_IDX_DomainName" ON "EmailAddress"."EmailAddress" ("DomainName");
