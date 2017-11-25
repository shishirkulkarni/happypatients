#!/bin/bash
for i in {0..16383}; do redis-cli CLUSTER ADDSLOTS $i; done;
