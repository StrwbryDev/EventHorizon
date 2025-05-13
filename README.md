</center>

[![Modrinth][modrinth-shield]][modrinth-url]
[![Discord][discord-shield]][discord-url]
[![GitHub][github-shield]][github-url]
[![Paypal][paypal-shield]][paypal-url]
[![Supported Minecraft Versions][versions-shield]][versions-url]
</center>

# EventHorizon
EventHorizon is an interactive game mode plugin, originally developed for the KSU Esports Minecraft server, aimed at boosting replayability and player engagement. It introduces random, server-wide events, categorized as positive, negative, or neutral, that challenge players to adapt and survive in both solo and team-based gameplay. These unpredictable occurrences create a dynamic and thrilling environment. EventHorizon can run as a standalone game mode or integrate seamlessly with other game mode plugins, such as survival games, speedruns, TaskCrafters, etc. 

## Dependencies
- FastAsyncWorldEdit
  - Not required, but block modification events will be automatically disabled without it
- PlaceholderAPI
  - Not required, but you will not see the tournament scoreboard without it
- Recommended: TAB (can use any TAB/Scoreboard plugin that supports PlaceholderAPI)
  - Add this code to the TAB config file to use our scoreboard
```
scoreboards:
    scoreboard-1.20.3+:
      title: "<#E0B11E>EventHorizon</#FF0000>"
      display-condition: "%player-version-id%>=765;%bedrock%=false" # Only display it to players using 1.20.3+ AND NOT bedrock edition
      lines:
        - "<light_purple>Remaining Time:"
        - "<white>%eventhorizon_remainingtime_formatted%"
```

## Commands
| Command                                  | Description                                      |
|------------------------------------------|--------------------------------------------------|
| `/eventhorizon begin`                   | Begins the tournament                           |
| `/eventhorizon end`                     | Concludes the tournament                        |
| `/eventhorizon pause`                   | Pauses the tournament                           |
| `/eventhorizon resume`                  | Continues a paused tournament                   |
| `/eventhorizon trigger <eventName>`     | Manually triggers an event by name              |
| `/eventhorizon reloadconfig`            | Reloads the configuration file                  |
| `/eventhorizon help`                    | Displays available commands                     |

## Current Events
| Event Name           | Event Type           | Event Classification | Brief Description |
|----------------------|----------------------|------------------------|-------------------|
| Fasting              | attribute            | negative               |Player hunger completely depletes|
| GrowthSpurt          | attribute            | neutral                |Player increases in size|
| HalfAHeart           | attribute            | negative               |Player health is set to half a heart|
| HoneyIShrunkTheKids  | attribute            | negative               |Player decrease in size|
| ZeroGravity          | attribute            | neutral                |Gravity is reduced for player|
| IceIsNice            | blockmodification    | neutral                |All blocks within a specific radius near the player will turn into packed ice blocks; polar bears and snow golems spawn|
| GoldRush             | blockmodification    | positive               |All stone-like blocks near the player turns into gold ore|
| NetherInvasion       | blockmodification    | negative               |Ground blocks -> nether blocks, water -> lava, plants -> fire, spawns nether mobs|
| DeepDarkInvasion     | blockmodification    | negative               |Ground blocks -> sculk and deepslate, plant -> ancient city blocks|
| BlockDropShuffle     | dropmodification     | neutral                |Shuffles block drops|
| DoubleOrNothing      | dropmodification     | negative               |Item drops have a 50% chance of dropping nothing and a 50% chance of double the amount of items|
| MobDropShuffle       | dropmodification     | neutral                |Shuffles mob drops|
| FoodComa             | effects              | neutral                |Player recieves saturation and slowness|
| GottaGoFast          | effects              | positive               |Player receives speed effect|
| Overmine             | effects              | positive               |Players can now mine twice as fast|
| SecondWind           | effects              | positive               |Player strength (attack power/damage dealt) is doubled|
| YoureTooSlow         | effects              | negative               |Player receives slowness effect|
| ButterFingers        | inventoryAdjustment  | negative               |The player drops items in their hands at random intervals|
| FlightSchool         | inventoryAdjustment  | positive               |Player receives unbreakable elytra and 64 fireworks|
| InventorySwap        | inventoryAdjustment  | neutral                |Two players swap inventories with each other|
| SpoiledFood          | inventoryAdjustment  | negative               |All food items in the player's inventory turns into rotten flesh|
| DropParty            | itemspawn            | positive               |Random items are dropped near the player|
| Feast                | itemspawn            | positive               |Food items are dropped near the player|
| OreDropParty         | itemspawn            | positive               |Random ores are dropped near the player|
| ChickenFlock         | mobspawn             | positive               |Spawns a group of chickens around the player|
| CowHerd              | mobspawn             | positive               |Spawns a group of cows near the player|
| DropCreeper          | mobspawn             | negative               |Spawns a creeper near the player|
| EndRaid              | mobspawn             | negative               |Spawns end mobs around the player|
| NetherRaid           | mobspawn             | negative               |Spawns nether mobs around the player|
| RandomMobSpawn       | mobspawn             | neutral                |Spawns a random mob near the player (Ender Dragon is the only excluded mob)|
| WolfPack             | mobspawn             | negative               |Spawns a pack of angry wolves around the player|
| ZombieHorde          | mobspawn             | negative               |Spawns a group zombies around the player|
| ZombieInvasion       | mobspawn             | negative               |Continually spawns zombies around the player|

## Upcoming Features

### Beta (August at the latest)
- Events balance pass  
- Ignore players in spectator mode  
- All event values customizable in a config file  
- More events  

This list does not encompass everything we have planned, but we wanted to give a small preview on some of the front-end work we have planned. Much of the work for the beta release will be focused on back end refactoring, optimization, and bug fixes. 

## Help?
- Join our [Discord](https://discord.gg/dgjTQrUdjh) for questions or assistance.
- Check out our [JavaDocs](https://www.strwbry.dev/javadocs/eventhorizon/index.html). We're in the process of updating our pre-alpha documentation and will repost the more comprehensive docs soon. 

[modrinth-shield]: https://img.shields.io/badge/Download-00AF5C?logo=modrinth&logoColor=white&style=for-the-badge

[modrinth-url]: https://modrinth.com/plugin/eventhorizon

[discord-shield]: https://img.shields.io/badge/Discord-5865F2?logo=discord&logoColor=white&style=for-the-badge

[discord-url]: https://discord.gg/dgjTQrUdjh

[github-shield]: https://img.shields.io/badge/Source-181717?logo=github&logoColor=white&style=for-the-badge

[github-url]: https://github.com/StrwbryDev/EventHorizon

[paypal-shield]: https://img.shields.io/badge/Donate-00457C?logo=paypal&logoColor=white&style=for-the-badge

[paypal-url]: https://www.paypal.com/donate/?hosted_button_id=HETHDB2LD3X26

[versions-shield]: https://img.shields.io/badge/1.21.4+-blue?style=for-the-badge&label=Minecraft%20Versions

[versions-url]: https://modrinth.com/plugin/eventhorizon
