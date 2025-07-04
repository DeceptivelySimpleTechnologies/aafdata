-- NOTE: Run this script as the custom AafCoreModeler database role/account, which should be created by the AafCoreOwner role.
-- Table: InternetDomainLabelHierarchy.InternetDomainLabelHierarchy

-- DROP TABLE "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy";

CREATE TABLE "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy"
(
    "Id" bigint NOT NULL,
    "Uuid" uuid NOT NULL,
    "EntitySubtypeId" bigint NOT NULL,
    "TextKey" character varying(200) COLLATE pg_catalog."default" NOT NULL,

    "ParentInternetDomainLabelId" bigint NOT NULL,
    "ChildInternetDomainLabelId" bigint NOT NULL,

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

    CONSTRAINT "IntDomLblHir_PK" PRIMARY KEY ("Id"),

    CONSTRAINT "IntDomLbl_CHK_TextKey" CHECK ("TextKey" ~* '^[a-z0-9-]+$'),

    CONSTRAINT "IntDomLbl_UQ1_TextKey_DeletedAtDateTimeUtc" UNIQUE ("TextKey", "DeletedAtDateTimeUtc"),
    CONSTRAINT "IntDomLbl_UQ1_ParentIntDomLblId_ChildIntDomLblId_DeletedAtDateTimeUtc" UNIQUE ("ParentInternetDomainLabelId", "ChildInternetDomainLabelId", "DeletedAtDateTimeUtc"),

    CONSTRAINT "IntDomLbl_FK_EntitySubtypeId" FOREIGN KEY ("EntitySubtypeId") REFERENCES "EntitySubtype"."EntitySubtype"("Id"),
    CONSTRAINT "IntDomLbl_FK_ParentInternetDomainLabelId" FOREIGN KEY ("ParentInternetDomainLabelId") REFERENCES "InternetDomainLabel"."InternetDomainLabel"("Id"),
    CONSTRAINT "IntDomLbl_FK_ChildInternetDomainLabelId" FOREIGN KEY ("ChildInternetDomainLabelId") REFERENCES "InternetDomainLabel"."InternetDomainLabel"("Id")
)

    TABLESPACE pg_default;

CREATE INDEX "IntDomLbl_IDX_ParentInternetDomainLabelId" ON "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy" ("ParentInternetDomainLabelId");
CREATE INDEX "IntDomLbl_IDX_ChildInternetDomainLabelId" ON "InternetDomainLabelHierarchy"."InternetDomainLabelHierarchy" ("ChildInternetDomainLabelId")
