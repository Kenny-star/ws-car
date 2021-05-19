#!/usr/bin/env bash
#
# Sample usage:
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#

# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}


# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

function testUrl() {
  url=$@
  if curl $url -ks -f -o /dev/null
  then
    echo "Ok"
    return 0
  else
      echo -n "not yet"
      return 1
  fi;
}

function setupTestdata() {
  body=\
'{"carId":1,"name":"name 1","model": "model 1", "cost": 10000.0, "provider": "provider 1",
  "release_year": 2001, "country": "country 1", "content": "content 1",
  "buildPrices":[
        {"buildPriceId":1,"seats": 1,"cost": 100000.0, "rims": 100.0, "bumper": 100.0,
        "automatic": "automatic 1", "color": "color 1", "ledLights": "ledLights 1",
        "gpsEmbedded": "gpsEmbedded 1", "content":"content 1"},
        {"buildPriceId":2,"seats": 2,"cost": 200000.0, "rims": 200.0, "bumper": 200.0,
        "automatic": "automatic 2", "color": "color 2", "ledLights": "ledLights 2",
        "gpsEmbedded": "gpsEmbedded 2", "content":"content 2"},
        {"buildPriceId":3,"seats": 3,"cost": 300000.0, "rims": 300.0, "bumper": 300.0,
        "automatic": "automatic 3", "color": "color 3", "ledLights": "ledLights 3",
        "gpsEmbedded": "gpsEmbedded 3", "content":"content 3"},
    ], "customer":[
        {"customerId":1,"firstName":"firstName 1", "lastName":"lastName 1", "age": 50, "phoneNumber": "phoneNumber 1",
        "emailAddress": "emailAddress 1", "lease": false, "finance": false, "content": "content 1"},
        {"customerId":2,"firstName":"firstName 2", "lastName":"lastName 2", "age": 60, "phoneNumber": "phoneNumber 2",
        "emailAddress": "emailAddress 2", "lease": false, "finance": false, "content": "content 2"},
        {"customerId":3,"firstName":"firstName 3", "lastName":"lastName 3", "age": 70, "phoneNumber": "phoneNumber 3",
        "emailAddress": "emailAddress 3", "lease": false, "finance": false, "content": "content 3"}
    ]}'
    recreateComposite 1 "$body"
    body=\
'{"carId":113,"name":"name 113","model": "model 113", "cost": 10000.0, "provider": "provider 113",
  "release_year": 2001, "country": "country 113", "content": "content 113",
  "customer":[
        {"customerId":1,"firstName":"firstName 1", "lastName":"lastName 1", "age": 50, "phoneNumber": "phoneNumber 1",
        "emailAddress": "emailAddress 1", "lease": false, "finance": false, "content": "content 1"},
        {"customerId":2,"firstName":"firstName 2", "lastName":"lastName 2", "age": 60, "phoneNumber": "phoneNumber 2",
        "emailAddress": "emailAddress 2", "lease": false, "finance": false, "content": "content 2"},
        {"customerId":3,"firstName":"firstName 3", "lastName":"lastName 3", "age": 70, "phoneNumber": "phoneNumber 3",
        "emailAddress": "emailAddress 3", "lease": false, "finance": false, "content": "content 3"}
]}'
    recreateComposite 113 "$body"
    body=\
'{"carId":213,"name":"name 213","model": "model 213", "cost": 10000.0, "provider": "provider 213",
  "release_year": 2001, "country": "country 213", "content": "content 213", "buildPrices":[
       {"buildPriceId":1,"seats": 1,"cost": 100000.0, "rims": 100.0, "bumper": 100.0,
        "automatic": "automatic 1", "color": "color 1", "ledLights": "ledLights 1",
        "gpsEmbedded": "gpsEmbedded 1", "content":"content 1"},
       {"buildPriceId":2,"seats": 2,"cost": 200000.0, "rims": 200.0, "bumper": 200.0,
        "automatic": "automatic 2", "color": "color 2", "ledLights": "ledLights 2",
        "gpsEmbedded": "gpsEmbedded 2", "content":"content 2"},
       {"buildPriceId":3,"seats": 3,"cost": 300000.0, "rims": 300.0, "bumper": 300.0,
        "automatic": "automatic 3", "color": "color 3", "ledLights": "ledLights 3",
        "gpsEmbedded": "gpsEmbedded 3", "content":"content 3"}
]}'
    recreateComposite 213 "$body"
}

function recreateComposite() {
    local carId=$1
    local composites=$2
    assertCurl 200 "curl -X DELETE http://$HOST:$PORT/car-composite/${carId} -s"
    curl -X POST http://$HOST:$PORT/car-composite -H "Content-Type:
    application/json" --data "$composites"
}

function waitForService() {
  url=$@
  echo -n "Wait for: $url... "
  n=0
  until testUrl $url
  do
    n=$((n + 1))
    if [[ $n == 100 ]]
    then
      echo " Give up"
      exit 1
      else
        sleep 6
        echo -n ", retry #$n "
    fi
  done
}

set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
     then
echo "Restarting the test environment..."
echo "$ docker-compose down"
  docker-compose down
echo "$ docker-compose up -d"
  docker-compose up -d
fi

#waitForService http://$HOST:${PORT}/product-composite/1

waitForService curl -X DELETE http://$HOST:$PORT/car-composite/13

setupTestdata

# Verify that a normal request works, expect three recommendations and three reviews

#assertCurl 200 "curl http://$HOST:$PORT/car-composite/1 -s"
#assertEqual 1 $(echo $RESPONSE | jq .carId)
#assertEqual 3 $(echo $RESPONSE | jq ".buildPrice | length")
#assertEqual 3 $(echo $RESPONSE | jq ".customer | length")

# Verify that a 404 (Not Found) error is returned for a non existing productId (13)

assertCurl 404 "curl http://$HOST:$PORT/car-composite/13 -s"

# Verify that no recommendations are returned for productId 113

assertCurl 200 "curl http://$HOST:$PORT/car-composite/113 -s"
assertEqual 113 $(echo $RESPONSE | jq .carId)
assertEqual 0 $(echo $RESPONSE | jq ".buildPrice | length")
assertEqual 3 $(echo $RESPONSE | jq ".customer | length")

# Verify that no reviews are returned for productId 213

assertCurl 200 "curl http://$HOST:$PORT/car-composite/213 -s"
assertEqual 213 $(echo $RESPONSE | jq .carId)
assertEqual 3 $(echo $RESPONSE | jq ".buildPrice | length")
assertEqual 0 $(echo $RESPONSE | jq ".customer | length")

# Verify that a 422 (Unprocessable Entity) error is returned for a productId that is out of range (-1)

assertCurl 422 "curl http://$HOST:$PORT/car-composite/-1 -s"
assertEqual "\"Invalid carId: -1\"" "$(echo $RESPONSE | jq .message)"


# Verify that a 400 (Bad Request) error error is returned for a productId that is not a number, i.e. invalid format

assertCurl 400 "curl http://$HOST:$PORT/car-composite/invalidCarId -s"
assertEqual "\"Type mismatch.\"" "$(echo $RESPONSE | jq .message)"

if [[ $@ == *"stop"* ]]
then
       echo "We are done, stopping the test environment..."
       echo "$ docker-compose down"
       docker-compose down
fi