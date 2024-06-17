# Telegram Message

Reads standard input and sends to Telegram

## Installation

- Requires Java runtime version 8 or later.
- [Download](https://github.com/mnellemann/tmsg/releases) and install the .rpm or .deb package and execute ```/opt/tmsg/bin/tmsg```
- Or [download](https://github.com/mnellemann/tmsg/releases) the .jar file and execute ```java -jar /path/to/tmsg-x.y.z-all.jar```

## Usage

```shell
Usage: tmsg [-hmV] -i=<chatId> -t=<token>
  -h, --help               Show this help message and exit.
  -i, --chat-id=<chatId>   Telegram Chat ID.
  -m, --markdown           Enable markdown parser.
  -t, --token=<token>      Telegram Token.
  -V, --version            Print version information and exit.
```
