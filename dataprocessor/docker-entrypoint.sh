#!/bin/bash

/opt/flink-1.18.1/bin/flink run -m jobmanager:8081 -c org.example.Main /opt/dataprocessor.jar