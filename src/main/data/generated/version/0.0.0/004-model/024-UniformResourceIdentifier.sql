-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: UniformResourceIdentifier.UniformResourceIdentifier

-- DROP TABLE "UniformResourceIdentifier"."UniformResourceIdentifier";

CREATE TABLE "UniformResourceIdentifier"."UniformResourceIdentifier"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "LocalizedName" character varying(100) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedDescription" character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    "LocalizedAbbreviation" character varying(15) COLLATE pg_catalog."default" NOT NULL,

    "Scheme" character varying(15) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "UserInfo" character varying(64) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "DomainName" character varying(255) COLLATE pg_catalog."default" NOT NULL,
    "InternetDomainLabelHierarchyId" bigint NOT NULL,
    "Port" character varying(5) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Path" character varying(2000) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Query" character varying(2000) COLLATE pg_catalog."default" NOT NULL DEFAULT '',
    "Fragment" character varying(500) COLLATE pg_catalog."default" NOT NULL DEFAULT '',

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

    CONSTRAINT "UniformResourceIdentifier_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "UniformResourceIdentifier_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "UniformResourceIdentifier_UQ1_LocalizedName_DeletedAtDateTimeUtc" UNIQUE ("LocalizedName", "DeletedAtDateTimeUtc"),

    CONSTRAINT "UniformResourceIdentifier_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype" ("Id"),

    CONSTRAINT "UniformResourceIdentifier_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$')
)

    TABLESPACE pg_default;

CREATE INDEX "UniformResourceIdentifier_IDX_LocalizedName" ON "UniformResourceIdentifier"."UniformResourceIdentifier" ("LocalizedName");
CREATE INDEX "UniformResourceIdentifier_IDX_DomainName" ON "UniformResourceIdentifier"."UniformResourceIdentifier" ("DomainName");
