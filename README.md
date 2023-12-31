Purpose of this library:
during development and testing, when crash happens, you will get a notification autonatically and won't need to search the logcat.

How to setup:

Add:

Logexplorer.monitorLogs(this@MainActivity)

To your launcher activity or Application init/onCreate block.

when crash happens, you will get a notification, no need to search the logs, sweet!

<img width="233" alt="Screenshot 2023-12-20 at 7 27 56 PM" src="https://github.com/nathannak/logexplorer/assets/17170507/e27e4e5a-2d5e-4df3-9a54-8ff3ac9dc5a9">
