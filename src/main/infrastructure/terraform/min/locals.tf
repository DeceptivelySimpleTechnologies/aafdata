terraform {
  backend "s3" {}
}

data "aws_region" "current" {}
data "aws_caller_identity" "current" {}
data "aws_availability_zones" "available" {}

data "aws_route53_zone" "public_domain_hosted_zone" {
  name         = local.domain
  private_zone = false
}

locals {
  application = var.application
  domain      = var.domain
  website     = "${local.application}.${local.domain}"
  environment = var.environment
  saml_provider_arn = var.saml_provider_arn
  self_service_saml_provider_arn = var.self_service_saml_provider_arn
  region = var.region

  name   = "ecs-ex-${replace(basename(path.cwd), "_", "-")}"
  
  user_data = <<-EOT
    #!/bin/bash
    cat <<'EOF' >> /etc/ecs/ecs.config
    ECS_CLUSTER=${local.application}
    ECS_LOGLEVEL=debug
    EOF
  EOT

  user_data_ml = <<-EOT
    #!/bin/bash
    cat <<'EOF' >> /etc/ecs/ecs.config
    ECS_CLUSTER=${local.application}
    ECS_LOGLEVEL=debug
    ECS_ENABLE_GPU_SUPPORT=true
    EOF
  EOT

  acm_certificate_arn = var.acm_certificate_arn

  vpc_config = {
    vpc_name                       = "${var.application}-${var.environment}"
    vpc_availability_zones         = var.azs_count
    vpc_cidr_block                 = var.vpc_cidr
    vpc_public_subnet_cidr_blocks  = local.public_cidrs
    vpc_private_subnet_cidr_blocks = local.private_cidrs
    vpc_data_subnet_cidr_blocks    = local.database_cidrs
  }  

  tags = {
    Application = var.application
    Domain      = var.domain
    Terraform   = true
    Workspace   = terraform.workspace
    Environment = var.environment
  }

  # RDS
  database = {
    master_user_name      = var.rds_user_name
    engine_version        = var.rds_engine_version
    deletion_protection   = var.rds_deletion_protection
    rds_instance_cluster_configuration = var.rds_instance_cluster_configuration
  }

  cidrs          = chunklist(cidrsubnets(var.vpc_cidr, [for i in range(var.azs_count * 3) : var.vpc_offset]...), var.azs_count)
  public_cidrs   = local.cidrs[0]
  private_cidrs  = local.cidrs[1]
  database_cidrs = local.cidrs[2]
}
