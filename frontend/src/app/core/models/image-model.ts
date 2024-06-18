import {TagModel} from "./tag-model";

export interface ImageModel {
  id: number | null;
  content: string | null;
  thumbnail: string | null;
  name: string | null;
  description: string | null;
  mood: string| null;
  tags: TagModel[];

}
