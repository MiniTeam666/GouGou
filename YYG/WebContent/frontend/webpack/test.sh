
#!/bin/sh
shopt -s extglob
ip=`awk '/inet / && $2 != "127.0.0.1"{print $2}' < (ifconfig) `
