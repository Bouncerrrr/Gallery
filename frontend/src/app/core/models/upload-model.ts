import {TagModel} from "./tag-model";

export interface UploadModel {
  id: number | null;
  content: string | null;
  name: string | null;
  description: string | null;
  mood: string| null;
  tags: string | null;
}
