To edit the site locally:
- Install hugo http://gohugo.io

To see the edits you are doing:
~/Apps/hugo/hugo server --theme=solstice --watch -b http://127.0.0.1/egerrit

To build the site like it is done on hudson:
hugo -b https://www.eclipse.org/egerrit/ -D
