cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets KycEntityFragment KycEntityFragmentAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack; do
  cd ../${pkg}
  if git diff-index --quiet HEAD --; then
    git pull;
  else
    git stash;
    git pull;
    git stash pop;
  fi
done
