resource "aws_cloudwatch_log_group" "logs" {
  name = random_string.this.id
}

# VPC 
resource "aws_security_group" "endpoint_securitygroup" {
  name        = "${local.application}-vpc-endpoint-${random_string.this.id}"
  description = "Allow TLS inbound traffic"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description      = "TLS from VPC"
    from_port        = 443
    to_port          = 443
    protocol         = "tcp"
    cidr_blocks      = [var.vpc_cidr]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  lifecycle {
    create_before_destroy = true
  }    

  tags = local.tags 
}


module "vpc" {
  aws_region = local.region

  name = "${local.application}-vpc-${local.environment}"
  cidr = var.vpc_cidr
  azs  = slice(data.aws_availability_zones.available.names, 0, var.azs_count)

  enable_nat_gateway     = true
  single_nat_gateway     = false
  one_nat_gateway_per_az = true
  enable_dns_hostnames   = true
  enable_dhcp_options    = true

  # Endpoints
  enable_s3_endpoint = true

  enable_apigw_endpoint              = true
  apigw_endpoint_private_dns_enabled = true
  apigw_endpoint_security_group_ids  = [aws_security_group.endpoint_securitygroup.id]
  apigw_endpoint_subnet_ids          = module.vpc.private_subnets

  enable_sqs_endpoint              = true
  sqs_endpoint_private_dns_enabled = true
  sqs_endpoint_security_group_ids  = [aws_security_group.endpoint_securitygroup.id]
  sqs_endpoint_subnet_ids          = module.vpc.private_subnets

  # Public subnets
  public_subnets               = local.public_cidrs
  public_dedicated_network_acl = true

  # Application subnets
  private_subnets               = local.private_cidrs
  private_subnet_suffix         = "private"
  private_dedicated_network_acl = true

  # Database subnets
  custom_subnets = {
    database = {
      subnets                   = local.database_cidrs
      subnet_suffix             = "database-intra"
      dedicated_network_acl     = true
      create_subnet_route_table = true
    }
  }  

  tags = local.tags 
}


module "vpn_saml" {
  source = "./modules/vpn_saml"

  domain_name = local.domain
  project     = local.application
  environment = local.environment

  association_subnet_id = module.vpc.private_subnets[0]
  associated_subnet_cidr = [module.vpc.vpc_cidr_block]
  route_subnet_cidr = []

  saml_provider_arn = local.saml_provider_arn
  self_service_saml_provider_arn = local.self_service_saml_provider_arn
  public_domain_hosted_zone = data.aws_route53_zone.public_domain_hosted_zone.id

  tags = local.tags
}