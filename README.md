# Discord DatHost Bot

Integrates with the DatHost API to start/stop/query CS:GO servers from discord directly instead of using the DatHost
dashboard.

# Getting started
1. Clone the repository
2. Build: `./gradlew build`
3. Run: `BOT_TOKEN=my_token USERNAME=dathostUsername PASSWORD=dathostPassword SERVER_ID=myServerId java -jar ./build/libs/discord-dathost-bot.jar` the environment variables must be set in order for the application to run properly.

See also the DatHost API Documentation: https://dathost.net/api