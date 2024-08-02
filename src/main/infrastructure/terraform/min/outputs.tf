output "output" {
    value = {
        AWS_REGION = data.aws_region.current.name
        DOMAIN = local.domain
        VPC_CIDR_BLOCK = var.vpc_cidr
        APPLICATION = local.application
        VPC_SUBNET_ID_1 = module.vpc.private_subnets[0]
        VPC_SUBNET_ID_2 = module.vpc.private_subnets[1]
    }
}
