# KtBots

KtBots is a simple bot program for creating many OfflineMode bots to test games.

# Usage

Current Version: 1.21.11 \
Minimum Java Version: 21 \
`java -jar KtBots-1.21.11.jar -ip <server_ip> [arguments]`

# Options
`-count <count>` Sets the amount of bots to connect. Default: 1
`-prefix <prefix>` Sets the prefix for all bot accounts to use

# Names

All real nicknames contained were generated based of real minecraft names currently expiring found using the labymod api. List was generated with the command 
`for ((i=0; i<50; i++)); do curl -s "https://laby.net/api/v3/names?order_by=available_from&order=ASC&min_length=1&max_length=16&page=$((i+1))" | jq -r '.[] | .name' >> nicks.txt; done`
