#!/bin/bash
BUILD_FORGE=/forge-build
SOURCEDIR=/mc_mods/TFC_CarpentersBlocks_adapter
BINDIR=/mc_mods
$MOD_V=$1
$TFC_V=$2
$CB_V=$3
$MC_V=$4
cd $BUILD_FORGE/mcp/src/minecraft
cp -r $SOURCEDIR/tfc_carpentersblocks_adapter .
cd $BUILD_FORGE/mcp
./recompile.sh
./reobfuscate.sh
cd deobf
cp -r $SOURCEDIR/META-INF .
cp -r $SOURCEDIR/mcmod.info .
JARFILE="TFC[$TFC_V]_carpentersblocks[$CB_V]_adapter_v[$MOD_V]_MC[$MC_V].jar"
jar cfM $JARFILE ./* 
mv $JARFILE $BINDIR
rm -rf /forge-buil/mcp/src/minecraft/tfc_carpentersblocks_adapter