    #!/bin/bash
 
    PATH="~/workspaces/SWIPERTOL/build/SWIPERTOLWebStack/SWIPERTOLWebStack-1.0/RHEL5_64/DEV.STD.PTHREAD/build/private/Apollo"
    FILE_PATH="/apollo/env/SWIPERTOL/Apollo/Manifest"
    FULL_PATH=($PATH'/')
    /bin/mkdir $PATH
    echo "Folder Created on location $PATH"
 
    /bin/cp $FILE_PATH $FULL_PATH
 
    echo "Copying Manifest File completed successfully"
 
    /bin/ls -ltrh $FULL_PATH
 
    PATH="~/workspaces/SWIPERTOL/build/SWIPERTOLWebStack/SWIPERTOLWebStack-1.0/RHEL5_64/DEV.STD.PTHREAD/build/private/var"
    FILE_PATH="/apollo/env/SWIPERTOL/var/state"
    FULL_PATH=($PATH'/')
    /bin/mkdir $PATH
 
    echo "Folder Created on location $PATH"
 
    /bin/cp -r $FILE_PATH $FULL_PATH
 
    echo "AAA files copied successfully from $FILE_PATH to $FULL_PATH"
