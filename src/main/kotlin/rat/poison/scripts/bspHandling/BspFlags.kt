package rat.poison.scripts.bspHandling

const val MAX_BRUSH_LIGHTMAP_DIM_WITHOUT_BORDER   = 32;
const val MAX_BRUSH_LIGHTMAP_DIM_INCLUDING_BORDER = 35;
const val MAX_DISP_LIGHTMAP_DIM_WITHOUT_BORDER    = 128;
const val MAX_DISP_LIGHTMAP_DIM_INCLUDING_BORDER  = 131;
const val MAX_LIGHTMAP_DIM_WITHOUT_BORDER         = MAX_DISP_LIGHTMAP_DIM_WITHOUT_BORDER;
const val MAX_LIGHTMAP_DIM_INCLUDING_BORDER       = MAX_DISP_LIGHTMAP_DIM_INCLUDING_BORDER;

const val  DIST_EPSILON              = 0.03125f;
const val  MAX_SURFINFO_VERTS        = 32;
const val BSPVERSION                = 19;
const val  HEADER_LUMPS              = 64;
const val  MAX_POLYGONS              = 50120;
const val  MAX_MOD_KNOWN             = 512;
const val  MAX_MAP_MODELS            = 1024;
const val  MAX_MAP_BRUSHES           = 8192;
const val  MAX_MAP_ENTITIES          = 4096;
const val  MAX_MAP_ENTSTRING         = 256 * 1024;
const val  MAX_MAP_NODES             = 65536;
const val  MAX_MAP_TEXINFO           = 12288;
const val  MAX_MAP_TEXDATA           = 2048;
const val  MAX_MAP_LEAFBRUSHES       = 65536;
const val  MIN_MAP_DISP_POWER        = 2;
const val  MAX_MAP_DISP_POWER        = 4;
const val  MAX_MAP_SURFEDGES         = 512000;
const val  MAX_DISP_CORNER_NEIGHBORS = 4;

/// NOTE: These are stored in a short in the engine now.  Don't use more than 16 bits
const val SURF_LIGHT     = 0x0001; /// value will hold the light strength
const val SURF_SLICK     = 0x0002; /// effects game physics
const val SURF_SKY       = 0x0004; /// don't draw, but add to skybox
const val SURF_WARP      = 0x0008; /// turbulent water warp
const val SURF_TRANS     = 0x0010;
const val SURF_WET       = 0x0020; /// the surface is wet
const val SURF_FLOWING   = 0x0040; /// scroll towards angle
const val SURF_NODRAW    = 0x0080; /// don't bother referencing the texture
const val SURF_H = 0x0100; /// make a primary bsp splitter
const val SURF_SKIP      = 0x0200; /// completely ignore, allowing non-closed brushes
const val SURF_NOLIGHT   = 0x0400; /// Don't calculate light
const val SURF_BUMPLIGHT = 0x0800; /// calculate three lightmaps for the surface for bumpmapping
const val SURF_HITBOX    = 0x8000; /// surface is part of a hitbox

const val CONTENTS_EMPTY         = 0;           /// No contents
const val CONTENTS_SOLID         = 0x1;         /// an eye is never valid in a solid
const val CONTENTS_WINDOW        = 0x2;         /// translucent, but not watery (glass)
const val CONTENTS_AUX           = 0x4;
const val CONTENTS_GRATE         = 0x8;         /// alpha-tested "grate" textures.  Bullets/sight pass through, but solids don't
const val CONTENTS_SLIME         = 0x10;
const val CONTENTS_WATER         = 0x20;
const val CONTENTS_MIST          = 0x40;
const val CONTENTS_OPAQUE        = 0x80;        /// things that cannot be seen through (may be non-solid though)
const val LAST_VISIBLE_CONTENTS  = 0x80;
const val ALL_VISIBLE_CONTENTS= LAST_VISIBLE_CONTENTS or LAST_VISIBLE_CONTENTS - 1;
const val CONTENTS_TESTFOGVOLUME = 0x100;
const val CONTENTS_UNUSED3       = 0x200;
const val CONTENTS_UNUSED4       = 0x400;
const val CONTENTS_UNUSED5       = 0x800;
const val CONTENTS_UNUSED6       = 0x1000;
const val CONTENTS_UNUSED7       = 0x2000;
const val CONTENTS_MOVEABLE      = 0x4000;      /// hits entities which are MOVETYPE_PUSH (doors, plats, etc.)
/// remaining contents are non-visible, and don't eat brushes
const val CONTENTS_AREAPORTAL    = 0x8000;
const val CONTENTS_PLAYERCLIP    = 0x10000;
const val CONTENTS_MONSTERCLIP   = 0x20000;
/// currents can be added to any other contents, and may be mixed
const val CONTENTS_CURRENT_0     = 0x40000;
const val CONTENTS_CURRENT_90    = 0x80000;
const val CONTENTS_CURRENT_180   = 0x100000;
const val CONTENTS_CURRENT_270   = 0x200000;
const val CONTENTS_CURRENT_UP    = 0x400000;
const val CONTENTS_CURRENT_DOWN  = 0x800000;
const val CONTENTS_ORIGIN        = 0x1000000;   /// removed before bsping an entity
const val CONTENTS_MONSTER       = 0x2000000;   /// should never be on a brush, only in game
const val CONTENTS_DEBRIS        = 0x4000000;
const val CONTENTS_DETAIL        = 0x8000000;   /// brushes to be added after vis leafs
const val CONTENTS_TRANSLUCENT   = 0x10000000;  /// set if any surface has trans
const val CONTENTS_LADDER        = 0x20000000;
const val CONTENTS_HITBOX        = 0x40000000;  /// use accurate hitboxes on trace

/// everyhting
const val MASK_ALL                   = 0xFFFFFFFF;
/// everything that is normally solid
const val MASK_SOLID                 = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_WINDOW or CONTENTS_MONSTER or CONTENTS_GRATE;
/// everything that blocks player movement
const val MASK_PLAYERSOLID           = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_PLAYERCLIP or CONTENTS_WINDOW or CONTENTS_MONSTER or CONTENTS_GRATE;
/// blocks npc movement
const val MASK_NPCSOLID              = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_MONSTERCLIP or CONTENTS_WINDOW or CONTENTS_MONSTER or CONTENTS_GRATE;
/// water physics in these contents
const val MASK_WATER                 = CONTENTS_WATER or CONTENTS_MOVEABLE or CONTENTS_SLIME;
/// everything that blocks line of sight
const val MASK_OPAQUE                = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_SLIME or CONTENTS_OPAQUE;
/// bullets see these as solid
const val MASK_SHOT                  = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_MONSTER or CONTENTS_WINDOW or CONTENTS_DEBRIS or CONTENTS_HITBOX;
/// non-raycasted weapons see this as solid (includes grates)
const val MASK_SHOT_HULL             = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_MONSTER or CONTENTS_WINDOW or CONTENTS_DEBRIS or CONTENTS_GRATE;
/// everything normally solid, except monsters (world+brush only)
const val MASK_SOLID_BRUSHONLY       = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_WINDOW or CONTENTS_GRATE;
/// everything normally solid for player movement, except monsters (world+brush only)
const val MASK_PLAYERSOLID_BRUSHONLY = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_WINDOW or CONTENTS_PLAYERCLIP or CONTENTS_GRATE;
/// everything normally solid for npc movement, except monsters (world+brush only)
const val MASK_NPCSOLID_BRUSHONLY    = CONTENTS_SOLID or CONTENTS_MOVEABLE or CONTENTS_WINDOW or CONTENTS_MONSTERCLIP or CONTENTS_GRATE;
/// just the world, used for route rebuilding
const val MASK_NPCWORLDconst        = CONTENTS_SOLID or CONTENTS_WINDOW or CONTENTS_MONSTERCLIP or CONTENTS_GRATE;
/// UNDONE: This is untested, any moving water
const val MASK_CURRENT               = CONTENTS_CURRENT_0 or CONTENTS_CURRENT_90 or CONTENTS_CURRENT_180 or CONTENTS_CURRENT_270 or CONTENTS_CURRENT_UP or CONTENTS_CURRENT_DOWN;
const val MASK_DEADSOLID             = CONTENTS_SOLID or CONTENTS_PLAYERCLIP or CONTENTS_WINDOW or CONTENTS_GRATE;