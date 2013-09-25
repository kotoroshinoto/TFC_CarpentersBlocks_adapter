#!/bin/bash
BUILD_FORGE=/forge-build
SOURCEDIR=/mc_mods/TFC_CarpentersBlocks_adapter
BINDIR=$SOURCEDIR/build
#./do_build_release.sh 0.0.3a 0.77.15 1.87 1.6.2
MOD_V="$1"
TFC_V="$2"
CB_V="$3"
MC_V="$4"
cd $BUILD_FORGE/mcp/src/minecraft
cp -r $SOURCEDIR/tfc_carpentersblocks_adapter .
cd $BUILD_FORGE/mcp
./recompile.sh
./reobfuscate.sh
cd reobf
cp -r $SOURCEDIR/META-INF .
cp -r $SOURCEDIR/mcmod.info .
JARFILE="TFC[$TFC_V]_carpentersblocks[$CB_V]_adapter_[$MOD_V]_MC[$MC_V].jar"
jar cfM $JARFILE ./* 
mv $JARFILE $BINDIR
rm -rf /forge-buil/mcp/src/minecraft/tfc_carpentersblocks_adapter