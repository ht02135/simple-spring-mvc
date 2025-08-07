provider "aws" {
  region = "eu-west-1"  # Replace with your preferred AWS region
}

resource "aws_cognito_user_pool" "user_pool" {
  name = "my-user-pool"
  alias_attributes = ["email"]
  auto_verified_attributes = ["email"]
  username_attributes = ["email"]
  admin_create_user_config {
    allow_admin_create_user_only = false
    invite_message_template {
      email_message = "Your username is {username} and temporary password is {####}."
      email_subject = "Your temporary password"
      sms_message = "Your username is {username} and temporary password is {####}."
    }
  }
  schema {
    attribute_data_type = "String"
    developer_only_attribute = false
    mutable = true
    name = "email"
    required = true
    string_attribute_constraints {
      max_length = "2048"
      min_length = "0"
    }
  }
}

resource "aws_cognito_user_pool_client" "user_pool_client" {
  name = "my-app-client"
  user_pool_id = aws_cognito_user_pool.user_pool.id
  allowed_oauth_flows = ["code"]
  allowed_oauth_scopes = ["openid"]
  allowed_oauth_flows_user_pool_client = true
  explicit_auth_flows = ["ALLOW_USER_PASSWORD_AUTH"]
  generate_secret = false
}

resource "aws_cognito_user_group" "user_group" {
  name        = "my-user-group"
  user_pool_id = aws_cognito_user_pool.user_pool.id
  description = "My user group description"
}

output "user_pool_id" {
  value = aws_cognito_user_pool.user_pool.id
}