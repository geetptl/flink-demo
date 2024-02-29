#!/bin/bash

/opt/flink/bin/flink run -m jobmanager:8081 -c org.example.Main /opt/dataprocessor.jar