package com.qxbytes.searcher;

public class PresetConstants {
	//This way of initializing can only be done in one fell swoop
	public static PB[] PRESET =  {
			new PB(
					"Standard Exclude",
					"",
					"Playing,Summoned,Changed,Teleported,Given,given,@,joined,left,killed,slain,Death,party,Request,request,online,Killed,Object,offline,Bomb,hurt,fell,Join,Quit,CombatLog,eliminated,game mode,Search,voting,voted,achievement,Teleporting,remaining,score,filled,placed"
				),
			new PB(
					"Standard Include",
					"whispers,messages,->,—>,",
					""
				),
			new PB(
					"Games Include",
					"Paintball,paintball,Quake,quake,SkyWars,skywars,Skywars,Walls,walls,Spleef,spleef,Parkour,parkour,CTF,ctf,Mob Arena,",
					""
				)
			
			};
}
