provider "aws" {
  region = var.aws_region
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "terraform-state-bucket"
  acl    = "private"
}

resource "aws_vpc" "aaf-data_min" {
  cidr_block = "10.0.0.0/16"
}

resource "aws_subnet" "public" {
  vpc_id            = aws_vpc.aaf-data_min.id
  cidr_block        = "10.0.1.0/24"
  map_public_ip_on_launch = true
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.aaf-data_min.id
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.aaf-data_min.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
}

resource "aws_route_table_association" "public" {
  subnet_id      = aws_subnet.public.id
  route_table_id = aws_route_table.public.id
}

resource "aws_instance" "host" {
  ami           = "ami-0c55b159cbfafe1f0" # Amazon Linux 2 AMI
  instance_type = "t2.micro"
  key_name      = var.key_name
  subnet_id     = aws_subnet.public.id

  user_data = <<-EOF
              #!/bin/bash
              yum update -y
              amazon-linux-extras install docker -y
              service docker start
              usermod -a -G docker ec2-user
              docker run -d -a stdin -a stdout -a stderr -i -t --log-driver awslogs -p 127.0.0.1:5432:5432/tcp --name postgres-db postgres:14.12
              docker run -d -a stdin -a stdout -a stderr -i -t --log-driver awslogs -p 127.0.0.1:80:80/tcp --name entity-data-microservice my-docker-image???
              EOF

  tags = {
    Name = "${var.application_name}-${var.environment_name}-ec2"
  }
}

resource "aws_route53_zone" "main" {
  name = var.existing_domain_name
}

resource "aws_route53_record" "min" {
  zone_id = aws_route53_zone.main.zone_id
  name    = var.environment_name
  type    = "A"
  ttl     = "300"
  records = [aws_instance.host.public_ip]
}

output "instance_ip" {
  value = aws_instance.host.public_ip
}