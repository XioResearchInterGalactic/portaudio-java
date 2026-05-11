#!/bin/bash
aws codeartifact get-authorization-token --domain merlyn --domain-owner 619238747039 --region us-east-1 --query authorizationToken --output text
