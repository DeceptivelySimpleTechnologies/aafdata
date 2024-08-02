variable environment {
        description     = "Environment abbreviation"
        type            = string
        default         = "min"
}
variable region {
        description     = "AWS Region"
        type            = string
        default         = "us-east-2"
}
variable identifier {
        description     = "Infrastructure identifier, will be used to name the resources"
        type            = string
        default         = "aaf-data"
}
variable vpc_offset {
        description     = "Denotes the number of address locations added to a base address in order to get to a specific absolute address, Usually the 8-bit byte"
        type            = number
        default         = "4"
}
variable vpc_cidr {
        description     = "Classless Inter-Domain Routing (CIDR), IP addressing scheme"
        type            = string
        default         = "10.0.0.0/16"
}

variable "application" {
        description     = "Application Name"
        type            = string
        default         = "aaf"
}

# Kibana
variable "kibana_user_email" {
        description     = "Kibana User Email"
        type            = string
        default         = "admin@min.domain.com"
}

variable "kibana_user_password" {
        description     = "Kibana User Password"
        type            = string
        default         = "##KIBANA##"
}
