#!/bin/bash
openssl aes-256-cbc -K $encrypted_56caa02b44ce_key -iv $encrypted_56caa02b44ce_iv -in i-am-secret.txt.enc -out i-am-secret.txt -d
cat i-am-secret.txt
