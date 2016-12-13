#Shell script to update the EGerrit version going through each necessary files
#Initiate the scipt from releng since I put find ../. finds the root of EGerrit, org.eclipse.egerrit
#
update() {
OLD=$1
NEW=$2
echo "Updating EGerrit $OLD to $NEW ..."
find ../. -name MANIFEST.MF | xargs sed -i~ -e "s/Bundle-Version: $OLD.qualifier/Bundle-Version: $NEW.qualifier/"
find ../. -name feature.xml | xargs sed -i~ -e "s/$OLD.qualifier/$NEW.qualifier/"
find ../. -name pom.xml | xargs sed -i~ -e "s/<version>$OLD-SNAPSHOT<\/version>/<version>$NEW-SNAPSHOT<\/version>/"
find ../. -name category.xml | xargs sed -i~ -e "s/$OLD.qualifier/$NEW.qualifier/"
find ../. -name category.xml | xargs sed -i~ -e "s/version=$OLD.qualifier/version=$NEW.qualifier/"

}

update 1.2.0 1.3.0
 
